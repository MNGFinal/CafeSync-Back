package com.ohgiraffers.cafesyncfinalproject.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "가맹점 정보 DTO") // ✅ Swagger DTO 설명
public class FranchiseDTO {

    @Schema(description = "가맹점 코드", example = "1001")
    private int franCode;

    @Schema(description = "가맹점 이름", example = "강남점")
    private String franName;

    @Schema(description = "가맹점 주소", example = "서울특별시 강남구 테헤란로 123")
    private String franAddr;

    @Schema(description = "가맹점 전화번호", example = "02-1234-5678")
    private String franPhone;

    @Schema(description = "가맹점 대표 이미지 URL", example = "https://firebase.com/image.jpg")
    private String franImage;

    @Schema(description = "가맹점 메모 (추가 정보)", example = "24시간 영업, 주차 가능")
    private String memo;

    @Schema(description = "점장 코드", example = "1")
    private int empCode;
}
