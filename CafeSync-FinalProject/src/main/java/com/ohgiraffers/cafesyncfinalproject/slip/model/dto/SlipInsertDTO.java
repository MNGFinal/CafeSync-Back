package com.ohgiraffers.cafesyncfinalproject.slip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "전표(Insert/Update) DTO")
public class SlipInsertDTO {

    @Schema(description = "전표 코드(자동 증가)", example = "1")
    private int slipCode;       // auto_increment 이지만, UPDATE 시 필요할 수 있음

    @Schema(description = "전표 날짜", example = "2025-01-15")
    private LocalDateTime slipDate;    // date 타입이지만, 문자열로 받을 수도 있음 (ex. "YYYY-MM-DD")

    @Schema(description = "거래처 코드", example = "10001")
    private int venCode;        // tbl_vendor의 PK라면 int/long 등 사용

    @Schema(description = "전표 구분", example = "출금")
    private String slipDivision;

    @Schema(description = "계정과목 코드", example = "501")
    private int actCode;

    @Schema(description = "적요 코드", example = "S001")
    private String summaryCode; // varchar(255)

    @Schema(description = "차변(출금)", example = "50000")
    private int debit;

    @Schema(description = "대변(입금)", example = "0")
    private int credit;

    @Schema(description = "가맹점 코드", example = "3001")
    private int franCode;       // tbl_franchise의 PK라면 int/long 등 사용
}
