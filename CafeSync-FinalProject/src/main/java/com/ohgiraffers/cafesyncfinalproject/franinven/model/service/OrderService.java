package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.OrderDetailRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.OrderRepository;
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
}
