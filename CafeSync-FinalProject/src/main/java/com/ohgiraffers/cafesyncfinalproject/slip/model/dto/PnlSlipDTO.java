package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PnlSlipDTO {

    private int id; // 식별자 PK
    private String pnlId;  // 손익 계산서 ID
    private int slipCode;  // 전표 ID

    private List<SlipDTO> slip;
}
