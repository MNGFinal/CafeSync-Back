package com.ohgiraffers.cafesyncfinalproject.schedule.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.entity.Schedule;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "ScheduleController", description = "가맹점 - 직원 관리 - 스케줄 관리")
public class ScheduleController {

    public final ScheduleService scheduleService;

    @Operation(
            summary = "계정 별 스케줄 조회",
            description = "계정 별(가맹점 별)로 스케줄을 전체 조회하여 캘린더에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스케줄 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "스케줄을 찾을 수 없음")
            }
    )
    @GetMapping("/schedule/{franCode}")
    public ResponseEntity<ResponseDTO> findSchedulesByFranCode(@PathVariable int franCode) {
        if (franCode <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "유효하지 않은 형식으로 잘못된 요청", null));
        }
        List<ScheduleDTO> schedules = scheduleService.findByFranCode(franCode);
        if (schedules.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "스케줄을 찾을 수 없음", null));
        }

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "스케줄 조회 성공", schedules));
    }

    @Operation(
            summary = "스케줄 신규 등록",
            description = "가맹점이 스케줄을 신규 등록하여 캘린더에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "스케줄 등록 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청")
            }
    )
    @PostMapping("/schedule")
    public ResponseEntity<ResponseDTO> registSchedule(@RequestBody List<ScheduleDTO> scheduleList) {
        if (scheduleList.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "유효하지 않은 형식으로 잘못된 요청", null));
        }
        List<ScheduleDTO> savedSchedules = scheduleService.saveSchedule(scheduleList);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED, "스케줄 등록 성공", savedSchedules));
    }

    @Operation(
            summary = "스케줄 수정",
            description = "가맹점이 기존 스케줄을 수정하여 캘린더에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스케줄 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @PutMapping("/schedule")
    public ResponseEntity<ResponseDTO> modifySchedule(@RequestBody List<ScheduleDTO> updatedScheduleRequest) {

        try {
            List<ScheduleDTO> modifiedSchedules = new ArrayList<>();

            for (ScheduleDTO modifiedSchedule : updatedScheduleRequest) {
                ScheduleDTO existingSchedule = scheduleService.findByScheduleCode(modifiedSchedule.getScheduleCode());

                if (existingSchedule == null) {
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(HttpStatus.NOT_FOUND, "스케줄을 찾을 수 없음: " + modifiedSchedule.getScheduleCode(), null));
                }

                existingSchedule.setEmpCode(modifiedSchedule.getEmpCode());
                existingSchedule.setScheduleDivision(modifiedSchedule.getScheduleDivision());
                existingSchedule.setScheduleDate(modifiedSchedule.getScheduleDate());
                existingSchedule.setEmpName(modifiedSchedule.getEmpName());

                System.out.println("set 한 existingSchedule = " + existingSchedule);

                ScheduleDTO savedSchedule = scheduleService.saveSchedule(existingSchedule);
                System.out.println("savedSchedule = " + savedSchedule);
                savedSchedule.setEmpName(existingSchedule.getEmpName());
                System.out.println("꼼수 최종 데이터 = " + savedSchedule);
                modifiedSchedules.add(savedSchedule);
            }
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "스케줄 수정 성공", modifiedSchedules));  // 수정된 스케줄 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", null));
        }
    }
}