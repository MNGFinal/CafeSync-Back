package com.ohgiraffers.cafesyncfinalproject.account.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "가맹점 정보 DTO") // ✅ DTO 설명 추가
public class FranchiseDTO {

    @Schema(description = "가맹점 코드", example = "1000")
    private int franCode;

    @Schema(description = "가맹점 이름", example = "강남점")
    private String franName;

    @Schema(description = "가맹점 주소", example = "서울특별시 강남구 테헤란로 123")
    private String franAddr;

    @Schema(description = "가맹점 전화번호", example = "02-1234-5678")
    private String franPhone;

    @Schema(description = "가맹점 대표 이미지 URL", example = "/images/franchise/1000.jpg")
    private String franImage;

    @Schema(description = "가맹점 메모", example = "강남 지역 대표 매장")
    private String memo;

    @Schema(description = "담당 직원 코드 (외래키)", example = "3")
    private int empCode;
}
