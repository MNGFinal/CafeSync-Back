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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hq")
@Tag(name = "가맹점 관련 스웨거 연동")
public class FranController {

    public final FranService franService;

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

}