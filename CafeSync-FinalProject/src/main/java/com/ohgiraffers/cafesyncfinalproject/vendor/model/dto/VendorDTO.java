package com.ohgiraffers.cafesyncfinalproject.vendor.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "거래처 정보를 담은 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendorDTO {

    @Schema(description = "거래처 코드", example = "10001")
    private int venCode;

    @Schema(description = "거래처 명", example = "하나로마트")
    private String venName;

    @Schema(description = "사업자 등록 번호", example = "111-11-11111")
    private String businessNum;

    @Schema(description = "거래처 이미지 URL", example = "http://example.com/image.png")
    private String venImage;

    @Schema(description = "대표자(담당자) 명", example = "홍길동")
    private String venOwner;

    @Schema(description = "거래처 주소", example = "서울특별시 강남구 테헤란로 123")
    private String venAddr;

    @Schema(description = "구분 (예: 일반, 금융, 카드 등)", example = "일반")
    private String venDivision;
}
