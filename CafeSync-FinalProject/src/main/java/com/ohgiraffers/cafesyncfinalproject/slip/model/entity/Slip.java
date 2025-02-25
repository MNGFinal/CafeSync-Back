package com.ohgiraffers.cafesyncfinalproject.slip.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_slip")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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

    @OneToMany(mappedBy = "slip", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Tax> taxes;

    @OneToMany(mappedBy = "slip", cascade = CascadeType.ALL, orphanRemoval = true) // ✅ 추가된 부분
    private List<PnlSlip> pnlSlips;  // ✅ Slip에 속한 PnlSlip 목록

    // 엔티티 업데이트 메서드(예시)
    public void updateFromDTO(LocalDateTime slipDate,
                              int venCode,
                              String slipDivision,
                              int actCode,
                              String summaryCode,
                              int debit,
                              int credit,
                              int franCode) {
        this.slipDate = slipDate;
        this.venCode = venCode;
        this.slipDivision = slipDivision;
        this.actCode = actCode;
        this.summaryCode = summaryCode;
        this.debit = debit;
        this.credit = credit;
        this.franCode = franCode;
    }
}
