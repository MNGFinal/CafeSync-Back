package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "가맹점 정보 DTO") // ✅ 전체 DTO 설명
public class FranchiseDTO {

    @Schema(description = "가맹점 코드 (PK)", example = "101")
    private int franCode;

    @Schema(description = "가맹점 이름", example = "강남점")
    private String franName;

    @Schema(description = "가맹점 주소", example = "서울특별시 강남구 테헤란로 123")
    private String franAddress;
}
