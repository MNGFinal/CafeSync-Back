package com.ohgiraffers.cafesyncfinalproject.slip.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.service.SlipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "SlipController", description = "가맹점 전표 관리 API")
public class SlipController {

    private final SlipService slipService;

    @Operation(
            summary = "특정 가맹점의 전표 목록 조회",
            description = "주어진 가맹점 코드와 날짜 범위에 해당하는 전표 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "204", description = "해당 날짜의 전표 데이터가 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류 발생")
            }
    )
    @GetMapping("/slip/{franCode}")
    public ResponseEntity<ResponseDTO> getFranSlipList(
            @Parameter(description = "가맹점 코드", example = "1000", required = true)
            @PathVariable("franCode") int franCode,

            @Parameter(description = "조회 시작 날짜 (YYYY-MM-DD)", example = "2024-02-01", required = true)
            @RequestParam("startDate") String startDate,

            @Parameter(description = "조회 종료 날짜 (YYYY-MM-DD)", example = "2024-02-28", required = true)
            @RequestParam("endDate") String endDate) {

        // 🔥 요청된 값 확인 (디버깅용)
        System.out.println("📥 프론트에서 받은 값: franCode = " + franCode + ", startDate = " + startDate + ", endDate = " + endDate);

        try {
            List<SlipDTO> slipList = slipService.findFranSlips(franCode, startDate, endDate);

            System.out.println("🔍 조회된 slipList: " + slipList);

            if (slipList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "해당 날짜의 데이터가 없습니다.", null));
            }

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "조회 성공", slipList));

        } catch (Exception e) {
            e.printStackTrace(); // ✅ 오류 발생 시 콘솔에 출력
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

}
