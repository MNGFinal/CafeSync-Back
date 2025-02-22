package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "거래처 정보 DTO") // ✅ DTO 전체 설명
public class VendorDTO {

    @Schema(description = "거래처 코드 (PK)", example = "2001")
    private int venCode;

    @Schema(description = "거래처명", example = "스타커피 유통")
    private String venName;

    @Schema(description = "사업자번호", example = "123-45-67890")
    private String businessNum;

    @Schema(description = "거래처 사진 URL", example = "https://example.com/images/vendor.png")
    private String venImage;

    @Schema(description = "거래처 대표 이름", example = "김상현")
    private String venOwner;

    @Schema(description = "거래처 주소", example = "서울특별시 강남구 테헤란로 123")
    private String venAddr;

    @Schema(description = "거래처 구분 (예: 원두 공급, 베이커리 공급 등)", example = "원두 공급")
    private String venDivision;

    // ✅ 일부 필드만 받는 생성자 추가
    public VendorDTO(int venCode, String venName) {
        this.venCode = venCode;
        this.venName = venName;
    }
}
