package com.ohgiraffers.cafesyncfinalproject.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "사용자 계정 정보 DTO") // ✅ Swagger DTO 설명
public class AccountDTO {

    @Schema(description = "사용자 아이디", example = "user123")
    private String userId;

    @Schema(description = "사용자 비밀번호", example = "P@ssw0rd!")
    private String userPass;

    @Schema(description = "사원 코드", example = "1001")
    private int empCode;

    @Schema(description = "이메일 주소", example = "user@example.com")
    private String email;

    @Schema(description = "권한 코드 (1: 관리자, 2: 직원)", example = "1")
    private int authority;

    @Schema(description = "직급 코드 (예: 101 - 매니저)", example = "101")
    private int jobCode;

    @Schema(description = "가맹점 코드 (본사: 10000, 가맹점: 해당 코드)", example = "10000")
    private int storeCode;
}
