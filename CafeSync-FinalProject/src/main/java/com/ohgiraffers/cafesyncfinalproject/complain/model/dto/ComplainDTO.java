package com.ohgiraffers.cafesyncfinalproject.complain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "컴플레인 DTO")
public class ComplainDTO {

    @Schema(description = "컴플레인 코드")
    private int complainCode;

    @Schema(description = "컴플레인 구분", example = "1=서비스, 2=위생, 3=기타")
    private int complainDivision;

    @Schema(description = "접수일자")
    private LocalDateTime complainDate;

    @Schema(description = "컴플레인 제출 고객 연락처")
    private String customerPhone;

    @Schema(description = "컴플레인 내용")
    private String complainDetail;

    @Schema(description = "가맹점 코드")
    private int franCode;

    @Schema(description = "직원 번호")
    private int empCode;

    // JOIN
    @Schema(description = "직원 이름(Employee join data)")
    private String empName;

}
