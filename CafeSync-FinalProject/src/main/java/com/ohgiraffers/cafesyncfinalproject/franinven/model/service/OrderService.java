package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.OrderDetailRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.OrderRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.InventoryDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.OrderDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.OrderDetailDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.Order;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.OrderDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final FirebaseStorageService firebaseStorageService;

    // 발주 신청
    @Transactional
    public boolean insertOrder(List<OrderDTO> orderDTOList) {
        try {
            // ✅ 여러 개의 발주 요청을 처리해야 함
            for (OrderDTO orderDTO : orderDTOList) {

                // ✅ 1. 먼저 tbl_order에 저장 (order_code 자동 생성)
                Order savedOrder = orderRepository.save(
                        Order.builder()
                                .franCode(orderDTO.getFranCode())
                                .orderDate(orderDTO.getOrderDate())
                                .orderStatus(orderDTO.getOrderStatus())
                                .build()
                );

                // ✅ 2. tbl_order_detail에 저장 (위에서 생성한 order_code 사용)
                List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                        .map(detailDTO -> OrderDetail.builder()
                                .order(savedOrder) // ✅ FK 매핑
                                .invenCode(detailDTO.getInvenCode())
                                .orderQty(detailDTO.getOrderQty())
                                .build())
                        .collect(Collectors.toList());

                orderDetailRepository.saveAll(orderDetails); // ✅ 배치 저장
            }

            return true; // 성공
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 실패
        }
    }

    // 발주 목록 조회
    public List<OrderDTO> getFranOrderList(int franCode) {
        // 1️⃣ 가맹점 코드로 발주 목록 조회
        List<Order> orders = orderRepository.findByFranCode(franCode);

        // 2️⃣ 발주 코드 목록을 가져옴
        List<Integer> orderCodes = orders.stream()
                .map(Order::getOrderCode)
                .collect(Collectors.toList());

        // 3️⃣ 네이티브 쿼리 실행 -> Object[] 리스트 반환
        List<Object[]> orderDetailsRaw = orderRepository.findOrderDetailsWithInventory(orderCodes);

        // 4️⃣ Object[] 데이터를 OrderDetailDTO로 변환하면서 Firebase 이미지 변환 추가
        List<OrderDetailDTO> orderDetails = orderDetailsRaw.stream()
                .map(obj -> {
                    String invenImage = (String) obj[7]; // 원본 이미지 URL
                    // ✅ Firebase Storage URL 변환
                    if (invenImage != null) {
                        invenImage = firebaseStorageService.convertGsUrlToHttp(invenImage);
                    }

                    return OrderDetailDTO.builder()
                            .orderDetailId(((Number) obj[0]).intValue())
                            .orderCode(((Number) obj[1]).intValue())
                            .invenCode((String) obj[2])
                            .orderQty(((Number) obj[3]).intValue())
                            .inventory(InventoryDTO.builder()
                                    .invenCode((String) obj[4])
                                    .invenName((String) obj[5])
                                    .expirationDate(((java.sql.Timestamp) obj[6]).toLocalDateTime()) // 날짜 변환 필요
                                    .invenImage(invenImage) // ✅ 변환된 이미지 URL 적용
                                    .build())
                            .build();
                })
                .collect(Collectors.toList());

        // 5️⃣ 주문 목록을 DTO로 변환
        return orders.stream()
                .map(order -> OrderDTO.builder()
                        .orderCode(order.getOrderCode())
                        .franCode(order.getFranCode())
                        .orderDate(order.getOrderDate())
                        .orderStatus(order.getOrderStatus())
                        .orderDetails(orderDetails.stream()
                                .filter(detail -> detail.getOrderCode() == order.getOrderCode()) // 주문 코드에 맞는 데이터만 매핑
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    // 발주 업데이트
    @Transactional
    public void updateFranOrderList(List<OrderDetailDTO> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new IllegalArgumentException("업데이트할 주문 목록이 없습니다.");
        }

        for (OrderDetailDTO dto : orderDetails) {
            if (dto.getOrderDetailId() == 0) {
                // ✅ 신규 데이터 → INSERT (orderCode 반드시 포함해야 함)
                Order order = orderRepository.findById(dto.getOrderCode())
                        .orElseThrow(() -> new IllegalArgumentException("해당 orderCode가 존재하지 않습니다: " + dto.getOrderCode()));

                OrderDetail newOrderDetail = OrderDetail.builder()
                        .order(order) // ✅ order 엔티티 매핑
                        .invenCode(dto.getInvenCode())
                        .orderQty(dto.getOrderQty())
                        .build();
                orderDetailRepository.save(newOrderDetail);
            } else {
                // ✅ 기존 데이터 → UPDATE
                orderDetailRepository.findById(dto.getOrderDetailId())
                        .ifPresent(existingOrder -> {
                            OrderDetail updatedOrder = existingOrder.update(dto);
                            orderDetailRepository.save(updatedOrder);
                        });
            }
        }
    }

    // ✅ 발주 상세 삭제 API
    @Transactional
    public void deleteOrderDetails(List<OrderDetailDTO> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new IllegalArgumentException("삭제할 주문 상세 내역이 없습니다.");
        }

        // ✅ 삭제할 orderDetailId 목록 추출
        List<Integer> orderDetailIds = orderDetails.stream()
                .map(OrderDetailDTO::getOrderDetailId)
                .collect(Collectors.toList());

        // ✅ 삭제 대상 존재 여부 확인
        List<OrderDetail> existingDetails = orderDetailRepository.findAllById(orderDetailIds);
        if (existingDetails.isEmpty()) {
            throw new IllegalArgumentException("삭제할 주문 상세 내역이 존재하지 않습니다.");
        }

        // ✅ 삭제 수행
        orderDetailRepository.deleteAll(existingDetails);
    }

    // // ✅ 발주 내역 삭제
    @Transactional
    public void deleteFranOrderList(List<OrderDTO> request) {

        for (OrderDTO orderDTO : request) {
            int orderCode = orderDTO.getOrderCode();

            // ✅ 1. 해당 발주의 상세 항목 먼저 삭제 (tbl_order_detail)
            orderDetailRepository.deleteByOrderOrderCode(orderCode);

            // ✅ 2. 발주 삭제 (tbl_order)
            orderRepository.deleteById(orderCode);
        }
    }
}
