package com.ohgiraffers.cafesyncfinalproject.slip.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_pnl_slip")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PnlSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 중간 테이블의 PK

    @ManyToOne
    @JoinColumn(name = "pnl_id", nullable = false)
    private Pnl pnl;  // 손익 계산서 (Many)

    @ManyToOne // ✅ Slip과 관계 추가
    @JoinColumn(name = "slip_code", referencedColumnName = "slip_code", nullable = false)
    private Slip slip; // ✅ 전표 ID를 Slip 엔티티와 매핑
}
