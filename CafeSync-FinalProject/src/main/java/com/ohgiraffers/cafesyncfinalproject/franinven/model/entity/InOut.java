package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_inout")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 적용
    @Column(name = "inout_code")
    private int inoutCode;

    @Column(name = "inout_division", nullable = false)
    private int inoutDivision; // 입출고 구분 (1: 입고, 2: 출고)

    @Column(name = "inout_date", nullable = false)
    private LocalDateTime inoutDate; // 입출고 날짜

    @Column(name = "inout_status", nullable = false)
    private int inoutStatus; // 입출고 상태 (예: 0: 대기, 1: 승인)

    @Column(name = "franout_code", nullable = false)
    private int franOutCode; // 출고 가맹점 코드

    @Column(name = "franin_code", nullable = false)
    private int franInCode; // 입고 가맹점 코드

    @Column(name = "inven_code", nullable = false, length = 255)
    private String invenCode; // 재고 코드

    @Column(name = "quantity", nullable = false)
    private int quantity; // 수량
}
