package com.ohgiraffers.cafesyncfinalproject.summary.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.summary.model.dto.SummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.summary.model.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fran")
@Tag(name = "Summary API", description = "적요(요약) 관련 API")  // ①
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/summary")
    @Operation(summary = "적요 조회", description = "적요(요약) 전체 목록을 조회합니다.") // ②
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "204", description = "해당 적요 정보가 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    public ResponseEntity<ResponseDTO> getSummaryList() {
        try {
            List<SummaryDTO> summaryList = summaryService.findSummaryList();
            System.out.println("🔍 조회된 적요 정보: " + summaryList);

            if (summaryList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "해당 적요 정보가 없습니다.", null));
            }

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "조회 성공", summaryList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }
}
