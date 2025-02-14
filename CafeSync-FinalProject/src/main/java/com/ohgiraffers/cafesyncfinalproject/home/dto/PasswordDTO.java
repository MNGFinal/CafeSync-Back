package com.ohgiraffers.cafesyncfinalproject.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(description = "비밀번호 변경 요청 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PasswordDTO {

    @Schema(description = "사용자 아이디", example = "user123")
    private String userId;

    @Schema(description = "새로운 비밀번호", example = "P@ssw0rd!")
    private String userPass;
}

