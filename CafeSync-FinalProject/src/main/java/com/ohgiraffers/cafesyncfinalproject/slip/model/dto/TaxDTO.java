package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "세금 계산서(Tax) DTO")
public class TaxDTO {

    @Schema(description = "세금 계산서 ID", example = "TAX001")
    private String taxId;    // VARCHAR(255)

    @Schema(description = "세금 계산서 날짜 (yyyy-MM-dd)", example = "2025-01-15")
    private String taxDate;  // String으로 받을 수도 있고, LocalDate로 받을 수도 있음

    @Schema(description = "연결된 전표 코드", example = "1")
    private int slipCode;    // tbl_slip의 PK

    @Schema(description = "세금 계산서 금액(차변/대변)", example = "5000")
    private int taxVal;

    @Schema(description = "가맹점 코드", example = "1000") // ✅ 추가됨
    private int franCode;
}
