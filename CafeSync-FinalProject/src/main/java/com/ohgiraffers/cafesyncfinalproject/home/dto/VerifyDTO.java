package com.ohgiraffers.cafesyncfinalproject.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "이메일 인증 요청 DTO") // ✅ Swagger DTO 설명
public class VerifyDTO {

    @Schema(description = "사용자 아이디", example = "user123")
    private String userId;

    @Schema(description = "이메일 주소", example = "user@example.com")
    private String email;

    @Schema(description = "이메일 인증번호 (8자리 대문자+숫자 조합)", example = "AB12CD34")
    private String authenticationNumber;
}
