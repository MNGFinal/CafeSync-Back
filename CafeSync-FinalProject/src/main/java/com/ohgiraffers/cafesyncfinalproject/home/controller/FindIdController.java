package com.ohgiraffers.cafesyncfinalproject.home.controller;

import com.ohgiraffers.cafesyncfinalproject.home.dto.AccountDTO;
import com.ohgiraffers.cafesyncfinalproject.home.dto.FranchiseDTO;
import com.ohgiraffers.cafesyncfinalproject.home.service.FindIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "아이디 찾기", description = "아이디 찾기 관련 API") // ✅ Swagger API 그룹
@RestController
@RequestMapping("/api/find-id")
@RequiredArgsConstructor
public class FindIdController {

    private final FindIdService findService;

    // ✅ 가맹점 리스트 조회 API
    @Operation(summary = "가맹점 리스트 조회", description = "등록된 가맹점 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "204", description = "데이터 없음")
    })
    @GetMapping("/findFranList")
    public ResponseEntity<List<FranchiseDTO>> getFranList() {
        List<FranchiseDTO> list = findService.getFranList();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content 반환
        }

        return ResponseEntity.ok(list); // 200 OK와 함께 리스트 반환
    }

    // ✅ 아이디 찾기 검증 API
    @Operation(summary = "아이디 찾기 검증", description = "입력한 정보로 아이디를 찾습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아이디 조회 성공"),
            @ApiResponse(responseCode = "404", description = "일치하는 계정 없음")
    })
    @PostMapping("/verifyUser")
    public ResponseEntity<?> verifyUser(
            @RequestBody @Parameter(description = "아이디 찾기에 필요한 정보") AccountDTO request) {

        // storeCode가 null이거나 0이면 10000으로 설정
        if (request.getStoreCode() == 0) {
            request.setStoreCode(10000);
        }

        String userId = findService.findUserId(request);

        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(404).body("일치하는 계정이 없습니다.");
        }
    }
}
