package com.ohgiraffers.cafesyncfinalproject.slip.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tbl_slip")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Slip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slip_code")
    private int slipCode;  // 전표 ID (PK)

    @Column(name = "slip_date", nullable = false)
    private LocalDateTime slipDate;  // 전표 날짜

    @Column(name = "ven_code", nullable = false)
    private int venCode;  // 거래처 코드

    @Column(name = "slip_division", nullable = false, length = 255)
    private String slipDivision;  // 구분

    @Column(name = "act_code", nullable = false)
    private int actCode;  // 계정과목 코드

    @Column(name = "summary_code", nullable = false, length = 255)
    private String summaryCode;  // 적요 코드

    @Column(name = "debit")
    private Integer debit;  // 차변(출금) (nullable)

    @Column(name = "credit")
    private Integer credit;  // 대변(입금) (nullable)

    @Column(name = "fran_code", nullable = false)
    private int franCode;  // 가맹점 코드
}
