package com.ohgiraffers.cafesyncfinalproject.summary.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "적요(요약) DTO")
public class SummaryDTO {

    @Schema(description = "적요 코드", example = "S001")
    private String summaryCode;

    @Schema(description = "적요명", example = "식사비")
    private String summaryName;
}
