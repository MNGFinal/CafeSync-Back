package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SummaryDTO {

    @Schema(description = "적요 코드", example = "S001")
    private String summaryCode; // 적요 코드

    @Schema(description = "적요 이름", example = "상품 매출")
    private String summaryName; // 적요 이름
}
