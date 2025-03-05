package com.ohgiraffers.cafesyncfinalproject.stat.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.SalesSummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.StatDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fran")
@Tag(name = "통계 관련 스웨거 연동")
public class StatController {

    private final StatService statService;

    @Operation(summary = "로그인한 가맹점의 매출 조회", description = "현재 로그인한 가맹점의 매출 데이터를 조회합니다.")
    @GetMapping("/sales/{franCode}")
    public ResponseEntity<ResponseDTO> salesStat(@PathVariable int franCode) {

        List<StatDTO> salesStat = statService.getSalesStat(franCode);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "매출 조회 성공", salesStat));
    }


    @GetMapping("/sales/summary")
    public ResponseEntity<SalesSummaryDTO> getSalesSummary(
            @RequestParam Integer franCode,  // ✅ 가맹점 코드
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,  // ✅ 시작 날짜
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate  // ✅ 종료 날짜
    ) {

        System.out.println("🟢 API 요청 받음 - franCode: " + franCode + ", 기간: " + startDate + " ~ " + endDate);


        // ✅ 날짜가 없으면 기본값 설정 (이번 달 1일 ~ 오늘)
        if (startDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1); // 이번 달 1일
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // 오늘 날짜
        }

        System.out.println("🟢 API 요청 - franCode: " + franCode + ", 기간: " + startDate + " ~ " + endDate);

        return ResponseEntity.ok(statService.getSalesSummary(franCode, startDate, endDate));
    }



}
