package com.ohgiraffers.cafesyncfinalproject.employee.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "스케줄 DTO")
public class ScheduleDTO {

    @Schema(description = "스케줄 날짜")
    private Date scheduleDate;

    @Schema(description = "스케줄 대상자")
    private int empCode;

    @Schema(description = "스케줄 구분", example = "0=휴가, 1=오픈, 2=미들, 3=마감")
    private int scheduleDivision;

    @Schema(description = "가맹점 코드")
    private int franCode;
}
