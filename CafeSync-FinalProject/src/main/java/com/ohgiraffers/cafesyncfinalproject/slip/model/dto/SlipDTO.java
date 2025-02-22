package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.VendorDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SlipDTO {

    @Schema(description = "전표 ID (PK)", example = "1")
    private int slipCode;

    @Schema(description = "전표 날짜", example = "2024-02-22")
    private LocalDateTime slipDate;

    @Schema(description = "거래처 정보", example = "{ \"venCode\": 101, \"venName\": \"카페싱크\" }")
    private VendorDTO venCode;

    @Schema(description = "전표 구분", example = "매출")
    private String slipDivision;

    @Schema(description = "계정과목 정보", example = "{ \"actCode\": 2001, \"actName\": \"상품 매출\", \"actDivision\": \"수익\" }")
    private ActDTO actCode;

    @Schema(description = "적요 코드 정보", example = "{ \"summaryCode\": \"S001\", \"summaryName\": \"상품 매출\" }")
    private SummaryDTO summaryCode;

    @Schema(description = "차변(출금) (nullable)", example = "10000", nullable = true)
    private Integer debit;

    @Schema(description = "대변(입금) (nullable)", example = "null", nullable = true)
    private Integer credit;

    @Schema(description = "가맹점 코드", example = "1")
    private int franCode;
}
