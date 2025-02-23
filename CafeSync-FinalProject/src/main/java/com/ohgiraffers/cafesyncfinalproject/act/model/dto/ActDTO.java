package com.ohgiraffers.cafesyncfinalproject.act.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "계정과목 DTO")
public class ActDTO {

    @Schema(description = "계정과목 코드", example = "1")
    private int actCode;

    @Schema(description = "계정과목 이름", example = "매출액")
    private String actName;

    @Schema(description = "계정과목 구분", example = "수익")
    private String actDivision;
}
