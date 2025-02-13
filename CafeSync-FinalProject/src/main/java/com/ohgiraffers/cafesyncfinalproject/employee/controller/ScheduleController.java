package com.ohgiraffers.cafesyncfinalproject.employee.controller;

import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Schedule;
import com.ohgiraffers.cafesyncfinalproject.employee.model.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran/schedule")
@RequiredArgsConstructor
@Tag(name = "ScheduleController", description = "가맹점 - 직원 관리 - 스케줄 관리")
public class ScheduleController {

    public final ScheduleService scheduleService;

    @GetMapping("/regist/{franCode}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByFranCode(@PathVariable int franCode) {
        System.out.println("franCode = " + franCode);
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByFranCode(franCode);
        return ResponseEntity.ok(schedules);
    }

}
