package com.ohgiraffers.cafesyncfinalproject.schedule.controller;

import com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "ScheduleController", description = "가맹점 - 직원 관리 - 스케줄 관리")
public class ScheduleController {

    public final ScheduleService scheduleService;

    // 스케줄 전체 조회 - 가맹점별
    @GetMapping("/schedule/{franCode}")
    public List<ScheduleDTO> getSchedulesByFranCode(@PathVariable int franCode) {
        System.out.println("schedule franCode = " + franCode);
        List<ScheduleDTO> schedules = scheduleService.findByFranCode(franCode);
        System.out.println("스케줄 컨트롤러 schedules = " + schedules);
        return schedules;
    }

    // 스케줄 구분별 조회 - 가맹점&날짜별
//    @GetMapping("/schedule/monthly/{franCode}")
//    public List<ScheduleDTO> getSchedulesWithDivisionsByFranCode(@PathVariable int franCode) {
//        System.out.println("컨트롤러 날짜별 조회시 franCode = " + franCode);
//        List <ScheduleDTO> monthlySchedules = scheduleService.findSchedulesWithDivisionsByFranCode(franCode);
//        System.out.println("monthlySchedules = " + monthlySchedules);
//        return monthlySchedules;
//    }

}