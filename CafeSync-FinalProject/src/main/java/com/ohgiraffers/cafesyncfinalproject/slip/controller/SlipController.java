package com.ohgiraffers.cafesyncfinalproject.slip.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.PnlDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.TaxDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.service.PnlService;
import com.ohgiraffers.cafesyncfinalproject.slip.model.service.SlipService;
import com.ohgiraffers.cafesyncfinalproject.slip.model.service.TaxService;
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
    private final TaxService taxService;
    private final PnlService pnlService;

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

        try {
            List<SlipDTO> slipList = slipService.findFranSlips(franCode, startDate, endDate);

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

    @Operation(
            summary = "가맹점 전표 저장(Insert/Update)",
            description = "프론트에서 넘어온 전표 목록(SlipInsertDTO)을 받아 DB에 Insert 또는 Update합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "저장 성공"),
                    @ApiResponse(responseCode = "500", description = "서버 오류 발생")
            }
    )
    @PostMapping("/slip")
    public ResponseEntity<ResponseDTO> saveSlipList(
            @RequestBody List<SlipInsertDTO> slipInsertList
    ) {

        try {
            // Service를 호출해 Insert/Update 수행
            slipService.saveSlipList(slipInsertList);

            // 성공 시
            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "저장 성공", null));

        } catch (Exception e) {
            e.printStackTrace();
            // 실패 시
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    @DeleteMapping("/slip")
    @Operation(
            summary = "전표 삭제",
            description = "전달받은 slipCode 리스트에 해당하는 전표들을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공"),
                    @ApiResponse(responseCode = "500", description = "서버 오류 발생")
            }
    )
    public ResponseEntity<ResponseDTO> deleteSlipList(
            @RequestBody List<Integer> slipCodes) {
        try {
            slipService.deleteSlipList(slipCodes);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "삭제 성공", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    @PostMapping("/tax")
    @Operation(
            summary = "세금 계산서 생성",
            description = "체크된 전표(차변, 대변) 정보를 바탕으로 세금 계산서를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "생성 성공"),
                    @ApiResponse(responseCode = "500", description = "서버 오류 발생")
            }
    )
    public ResponseEntity<ResponseDTO> createTax(
            @RequestBody List<TaxDTO> taxList
    ) {

        try {
            // 세금 계산서 생성 로직 (예: taxService.createTax(taxList))
            taxService.createTax(taxList);

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "세금 계산서 생성 성공", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    @PostMapping("/pnl")
    @Operation(
            summary = "손익 계산서 생성",
            description = "체크된 전표 리스트를 바탕으로 손익 계산서를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "손익 계산서 생성 성공"),
                    @ApiResponse(responseCode = "500", description = "서버 오류 발생")
            }
    )
    public ResponseEntity<ResponseDTO> createPnl(@RequestBody PnlDTO pnlDTO) {

        try {
            // ✅ 서비스 호출 → 손익 계산서 저장
            pnlService.createPnl(pnlDTO);

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "손익 계산서 생성 성공", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }
}
