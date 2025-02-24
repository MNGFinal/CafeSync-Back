package com.ohgiraffers.cafesyncfinalproject.act.controller;

import com.ohgiraffers.cafesyncfinalproject.act.model.dto.ActDTO;
import com.ohgiraffers.cafesyncfinalproject.act.model.service.ActService;
import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
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
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "Act API", description = "계정과목 관련 API")  // (1)
public class ActController {

    private final ActService actService;

    @GetMapping("/act")
    @Operation(summary = "계정과목 조회", description = "계정과목 전체 목록을 조회합니다.")  // (2)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "204", description = "해당 계정과목 정보가 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생")
    })
    public ResponseEntity<ResponseDTO> getActList() {
        try {
            List<ActDTO> actList = actService.findActList();
            System.out.println("🔍 조회된 계정과목 정보: " + actList);

            if (actList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "해당 계정과목 정보가 없습니다.", null));
            }

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "조회 성공", actList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }
}
