package com.ohgiraffers.cafesyncfinalproject.tax.model.dto;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranchiseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "세금 계산서 정보를 담는 DTO")
public class TaxDTO {

    @Schema(description = "세금 ID (기본키)", example = "TX12345")
    private String taxId;

    @Schema(description = "세금 적용 날짜", example = "2025-02-24")
    private String taxDate;

    @Schema(description = "연결된 전표 코드 (FK)", example = "1001")
    private int slipCode;  // ✅ slipCode는 int로 유지

    @Schema(description = "연결된 전표 상세 정보")
    private SlipDTO slip;  // ✅ 전표 상세 정보까지 포함해서 반환

    @Schema(description = "세금 금액", example = "30000")
    private int taxVal;

    @Schema(description = "가맹점 코드", example = "1000")
    private int franCode;

    @Schema(description = "가맹점 정보", example = "강남점")
    private FranchiseDTO franchise;
}
