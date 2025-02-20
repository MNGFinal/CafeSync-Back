package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

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
}
