package com.ohgiraffers.cafesyncfinalproject.stat.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.SalesSummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.StatDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<SalesSummaryDTO> getSalesSummary() {
        return ResponseEntity.ok(statService.getSalesSummary());
    }


}
