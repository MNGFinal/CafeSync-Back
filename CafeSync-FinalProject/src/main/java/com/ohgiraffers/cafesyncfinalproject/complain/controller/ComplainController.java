package com.ohgiraffers.cafesyncfinalproject.complain.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.complain.model.dto.ComplainDTO;
import com.ohgiraffers.cafesyncfinalproject.complain.model.service.ComplainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "ComplainController", description = "가맹점&본사 - 컴플레인")
public class ComplainController {

    public final ComplainService complainService;

    @Operation(
            summary = "가맹점 별 컴플레인 조회",
            description = "가맹점 별로 등록한 컴플레인을 전체 조회하여 화면에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "컴플레인 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "컴플레인을 찾을 수 없음")
            }
    )
    @GetMapping("/fran/complain/{franCode}")
    public ResponseEntity<ResponseDTO> findComplainsByFranCode(
            @PathVariable int franCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (franCode <= 0 || startDate == null || endDate == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "유효하지 않은 형식으로 잘못된 요청", null));
        }

        List<ComplainDTO> complains = complainService.findByFranCodeAndDateRange(franCode, startDate, endDate);
        if (complains.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "컴플레인을 찾을 수 없음", null));
        }

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "컴플레인 조회 성공", complains));
    }

}
