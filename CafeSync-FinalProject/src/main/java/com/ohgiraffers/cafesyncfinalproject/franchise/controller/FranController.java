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

    // ✅ 가맹점 전체조회 / 점장 조인
    @Operation(summary = "가맹점 목록 전체 조회",
            description = "모든 가맹점 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가맹점 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "가맹점 목록 조회 실패")
            })
    @GetMapping("/mgment")
    public ResponseEntity<ResponseDTO> findAllFran() {
        List<FranDTO> franList = franService.findAllFran();
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "가맹점 목록 조회 성공", franList));
    }

    // ✅ 가맹점 검색 (이름으로 검색)
    @Operation(summary = "가맹점 검색",
            description = "가맹점 이름을 입력하여 검색합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가맹점 검색 성공"),
                    @ApiResponse(responseCode = "400", description = "가맹점 검색 실패")
            })
    @GetMapping("/mgment/{query}")
    public ResponseEntity<ResponseDTO> findFranByName(@PathVariable("query") String query) {
        List<FranDTO> franList = franService.findFransByQuery(query);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "가맹점 검색 성공", franList));
    }

    // ✅ 가맹점 등록
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

    // ✅ 가맹점 수정
    @Operation(summary = "가맹점 수정",
            description = "가맹점 수정 기능", responses = {
            @ApiResponse(responseCode = "200", description = "가맹점 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "409", description = "가맹점 수정 충돌 (다른 프로세스에서 변경 중)"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PutMapping("/mgment/{franCode}") // ✅ URL에서 franCode 받기
    public ResponseEntity<ResponseDTO> modifyFran(
            @PathVariable int franCode,  // ✅ URL에서 franCode 값 받기
            @RequestBody FranDTO franDTO) { // ✅ 요청 바디에서 DTO 받기

        FranDTO franData = franService.modifyFran(franDTO);

        System.out.println("franDTO에 뭐가들었을까?" + franDTO);


        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "가맹점 수정 완료", franData));
    }

}