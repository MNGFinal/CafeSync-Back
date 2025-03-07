package com.ohgiraffers.cafesyncfinalproject.stat.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.*;
import com.ohgiraffers.cafesyncfinalproject.stat.model.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "통계 관련 스웨거 연동")
public class StatController {

    private final StatService statService;

    // 가맹점 매출 통계
    @Operation(
            summary = "로그인한 가맹점의 매출 조회",
            description = "현재 로그인한 가맹점의 매출 데이터를 조회합니다.",
            parameters = {
                    @Parameter(name = "franCode", description = "가맹점 코드", required = true, example = "1001")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "매출 조회 성공",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 (가맹점 코드 누락됨)"),
                    @ApiResponse(responseCode = "404", description = "해당 가맹점의 매출 데이터를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    @GetMapping("/fran/sales/{franCode}")
    public ResponseEntity<ResponseDTO> salesStat(@PathVariable int franCode) {

        List<StatDTO> salesStat = statService.getSalesStat(franCode);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "매출 조회 성공", salesStat));
    }

    // 가맹점 매출 검색 통계
    @Operation(
            summary = "가맹점 매출 요약 조회(검색)",
            description = "가맹점 코드를 기반으로 해당 가맹점의 매출 데이터를 요약하여 반환합니다. 날짜 범위를 지정할 수 있으며, 기본값은 현재 달의 1일부터 오늘까지입니다.",
            parameters = {
                    @Parameter(name = "franCode", description = "가맹점 코드", required = true, example = "1001"),
                    @Parameter(name = "startDate", description = "조회 시작 날짜 (YYYY-MM-DD)", required = false, example = "2025-01-01"),
                    @Parameter(name = "endDate", description = "조회 종료 날짜 (YYYY-MM-DD)", required = false, example = "2025-12-31")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 데이터를 반환함", content = @Content(schema = @Schema(implementation = SalesSummaryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 (franCode가 누락됨)"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    @GetMapping("/fran/sales/summary")
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

    // 메뉴별 통계
    @Operation(summary = "메뉴별 판매량 조회", description = "특정 가맹점에서 특정 기간 동안 판매된 메뉴 개수를 조회합니다.")
    @GetMapping("/fran/sales/menuStat")
    public ResponseEntity<List<MenuSalesDTO>> getMenuSales(
            @RequestParam Integer franCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(statService.getMenuSales(franCode, startDate, endDate));
    }








    // ✅ 가맹점별 매출 순위 조회
    @GetMapping("/hq/top-stores")
    public ResponseEntity<List<StoreSalesDTO>> getTopStores(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(statService.getTopStores(startDate, endDate));
    }

    // ✅ 메뉴별 판매 순위 조회
    @GetMapping("/hq/top-menus")
    public ResponseEntity<List<MenuSalesDTO>> getTopMenus(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(statService.getTopMenus(startDate, endDate));
    }

    // ✅ 오늘의 매출 순위 조회
    @GetMapping("/hq/today-sales")
    public ResponseEntity<List<TodaySalesDTO>> getTodaySales(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date today) {
        return ResponseEntity.ok(statService.getTodaySales(today));
    }

    // 검색
    @GetMapping("/hq/monthly-sales")
    public ResponseEntity<List<MonthlySalesDTO>> getMonthlySales(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(statService.getMonthlySales(startDate, endDate));
    }


}
