package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.OrderDetailDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ 자동 증가
    @Column(name = "order_detail_id")
    private int orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_code", nullable = false) // ✅ FK 매핑
    private Order order;

    @Column(name = "inven_code")
    private String invenCode;

    @Column(name = "order_qty")
    private int orderQty;

    // ✅ 기존 데이터를 새로운 객체로 변경하는 메서드 추가
    public OrderDetail update(OrderDetailDTO dto) {
        return OrderDetail.builder()
                .orderDetailId(this.orderDetailId) // 기존 ID 유지
                .order(this.order) // 기존 Order 유지 (변경할 필요 없음)
                .invenCode(dto.getInvenCode()) // 새로운 값 적용
                .orderQty(dto.getOrderQty()) // 새로운 값 적용
                .build();
    }
}
