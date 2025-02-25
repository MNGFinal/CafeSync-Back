package com.ohgiraffers.cafesyncfinalproject.slip.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "tbl_pnl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pnl {


    @Id
    @Column(name = "pnl_id", length = 255, nullable = false)
    private String pnlId;  // 손익 계산서 ID

    @Column(name = "period", nullable = false)
    private LocalDate period;  // 손익 계산서 기간

    @Column(name = "revenue", nullable = false)
    private int revenue;  // 총 수익

    @Column(name = "expense", nullable = false)
    private int expense;  // 총 비용

    @Column(name = "oper_profit", nullable = false)
    private int operProfit;  // 영업 이익

    @Column(name = "net_profit", nullable = false)
    private int netProfit;  // 순이익

    @Column(name = "ratio", length = 255, nullable = false)
    private String ratio;  // 비율

    @Column(name = "fran_code")
    private int franCode;

    @OneToMany(mappedBy = "pnl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PnlSlip> pnlSlips; // ✅ Pnl 삭제 시 관련된 PnlSlip도 삭제됨
}
