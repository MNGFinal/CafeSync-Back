package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ 자동 증가
    @Column(name = "order_code")
    private int orderCode;

    @Column(name = "fran_code")
    private int franCode;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_status")
    private int orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // ✅ OrderDetail과 연관관계
    private List<OrderDetail> orderDetails;
}
