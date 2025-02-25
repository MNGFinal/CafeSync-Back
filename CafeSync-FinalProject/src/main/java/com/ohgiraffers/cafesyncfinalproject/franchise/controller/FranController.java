package com.ohgiraffers.cafesyncfinalproject.franchise.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.service.FranService;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dto.FranDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hq")
@Tag(name = "가맹점 관련 스웨거 연동")
public class FranController {

    public final FranService franService;

    // 가맹점 전체조회
    @Operation(summary = "가맹점 목록 조회",
               description = "가맹점 목록의 전체 조회", responses = {
               @ApiResponse(responseCode = "200", description = "가맹점 목록 조회 성공"),
               @ApiResponse(responseCode = "400", description = "가맹점 목록 조회 실패")
    })
    @GetMapping("/mgment")
    public ResponseEntity<ResponseDTO> findAllFran() {

        List<FranDTO> franList = franService.findAllFran();

        System.out.println("franList컨트롤러단 = " + franList);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "가맹점 목록 조회 성공", franList);

        return ResponseEntity.ok(response);
    }

    // 가맹점 등록
    @Operation(summary = "가맹점 등록",
               description = "가맹점 등록 기능", responses ={
               @ApiResponse(responseCode = "201", description = "가맹점 등록 성공"),
               @ApiResponse(responseCode = "400", description = "가맹점 등록 실패")

    })
    @PostMapping("/mgment")
    public ResponseEntity<ResponseDTO> registFran(@RequestBody FranDTO franDTO) {

        FranDTO franData = franService.registFran(franDTO);

        System.out.println("franList = " + franData);

        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, "가맹점 등록 성공", franData);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    // ✅ 가맹점 삭제 (폐점)
    @Operation(summary = "가맹점 삭제",
            description = "가맹점 삭제 기능", responses ={
            @ApiResponse(responseCode = "204", description = "가맹점 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "삭제할 가맹점이 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")

    })
    @DeleteMapping("/mgment/{franCode}")
    public ResponseEntity<ResponseDTO> deleteFran(@PathVariable int franCode) {
        franService.deleteFran(franCode);

        return ResponseEntity.noContent().build();
    }
}