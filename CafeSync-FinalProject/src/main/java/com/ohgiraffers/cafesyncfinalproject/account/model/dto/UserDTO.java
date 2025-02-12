package com.ohgiraffers.cafesyncfinalproject.account.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "사용자 정보를 담는 DTO") // DTO 설명 추가
public class UserDTO {

    @Schema(description = "사용자 ID", example = "user123")
    private String userId;

    @Schema(description = "사용자 비밀번호", example = "password123")
    private String userPass;

    @Schema(description = "사원 코드", example = "1 (정수값)")
    private int empCode;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "사용자의 권한", example = "ADMIN or USER")
    private Role authority;

    @Schema(description = "직급 코드", example = "1~11 본사 직급 or 21, 22 가맹점 직급")
    private int jobCode;

    @Schema(description = "지점 코드", example = "1000번 부터 시작")
    private int storeCode;
}
