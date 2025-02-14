package com.ohgiraffers.cafesyncfinalproject.schedule.model.dto;

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

    @Schema(description = "스케줄 코드")
    private int scheduleCode;

    @Schema(description = "스케줄 날짜")
    private Date scheduleDate;

    @Schema(description = "스케줄 대상자")
    private int empCode;

    @Schema(description = "스케줄 구분", example = "0=휴가, 1=오픈, 2=미들, 3=마감")
    private int scheduleDivision;

    @Schema(description = "가맹점 코드")
    private int franCode;

    // 여기서부터는 기존에 없는 컬럼들
//    @Schema(description = "스케줄 구분 - 오픈")
//    private boolean hasOpen;
//
//    @Schema(description = "스케줄 구분 - 미들")
//    private boolean hasMiddle;
//
//    @Schema(description = "스케줄 구분 - 마감")
//    private boolean hasClose;
//
//    @Schema(description = "스케줄 구분 - 휴가")
//    private boolean hasVacation;
//
//    public ScheduleDTO(Date scheduleDate, boolean hasOpen, boolean hasMiddle, boolean hasClose, boolean hasVacation) {
//        this.scheduleDate = scheduleDate;
//        this.hasOpen = hasOpen;
//        this.hasMiddle = hasMiddle;
//        this.hasClose = hasClose;
//        this.hasVacation = hasVacation;
//    }
}
