package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranchiseDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PnlDTO {

    private String pnlId;  // 손익 계산서 ID
    private LocalDate period;  // 기간
    private int revenue;  // 수익
    private int expense;  // 비용
    private int operProfit;  // 영업 이익
    private int netProfit;  // 순이익
    private String ratio;  // 비율
    private int franCode; // 가맹점 코드

    // ✅ 다대다 관계 해결을 위한 전표 ID 리스트
    private List<PnlSlipDTO> slipCodes;
    private FranchiseDTO franchise;
}
