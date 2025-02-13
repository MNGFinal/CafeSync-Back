package com.ohgiraffers.cafesyncfinalproject.account.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "직급 정보 DTO")
public class JobDTO {

    @Schema(description = "직급 코드", example = "21")
    private int jobCode;

    @Schema(description = "직급 이름", example = "점장")
    private String jobName;
}
