package com.ohgiraffers.cafesyncfinalproject.tax.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.tax.model.dto.TaxDTO;
import com.ohgiraffers.cafesyncfinalproject.tax.model.service.TaxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @GetMapping("/tax/{franCode}")
    @Operation(summary = "가맹점별 세금 계산서 조회", description = "가맹점 코드 및 날짜 범위로 세금 계산서 리스트를 조회합니다.")
    public ResponseEntity<ResponseDTO> getFranTaxList(
            @Parameter(description = "가맹점 코드", example = "1000", required = true)
            @PathVariable("franCode") int franCode,

            @Parameter(description = "조회 시작 날짜 (YYYY-MM-DD)", example = "2025-02-01", required = true)
            @RequestParam("startDate") String startDate,

            @Parameter(description = "조회 종료 날짜 (YYYY-MM-DD)", example = "2025-02-28", required = true)
            @RequestParam("endDate") String endDate
    ) {
        try {
            List<TaxDTO> taxList = taxService.findFranTaxes(franCode, startDate, endDate);

            if (taxList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "해당 날짜의 데이터가 없습니다.", null));
            }

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "조회 성공", taxList));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    // 세금 계산서 삭제
    @DeleteMapping("/tax")
    @Operation(summary = "선택한 세금 계산서 삭제", description = "체크된 세금 계산서(tax_id)만 삭제합니다.")
    public ResponseEntity<ResponseDTO> deleteFranTaxes(
            @Parameter(description = "삭제할 세금 계산서 ID 목록", required = true)
            @RequestBody List<String> taxIds) {

        try {
            if (taxIds == null || taxIds.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "삭제할 세금 계산서가 없습니다.", null));
            }

            boolean isDeleted = taxService.deleteFranTaxes(taxIds);

            if (isDeleted) {
                return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "삭제 성공", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(HttpStatus.NOT_FOUND, "삭제할 데이터가 없습니다.", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }
}
