package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ActDTO {

    @Schema(description = "계정과목 코드", example = "101")
    private int actCode; // 계정과목 코드

    @Schema(description = "계정과목명", example = "매출")
    private String actName; // 계정과목명

    @Schema(description = "구분", example = "수익")
    private String actDivision; // 구분
}
