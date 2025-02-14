package com.ohgiraffers.cafesyncfinalproject.home.controller;

import com.ohgiraffers.cafesyncfinalproject.home.dto.PasswordDTO;
import com.ohgiraffers.cafesyncfinalproject.home.dto.VerifyDTO;
import com.ohgiraffers.cafesyncfinalproject.home.service.FindPwService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Tag(name = "비밀번호 찾기", description = "비밀번호 찾기 관련 API")
@RestController
@RequestMapping("/api/find-pass")
@RequiredArgsConstructor
public class FindPwController {

    private final FindPwService findPwService;

    // ✅ 아이디와 이메일 검증 후 이메일 전송
    @Operation(summary = "비밀번호 찾기 - 이메일 인증 요청", description = "아이디와 이메일을 검증한 후, 이메일로 인증번호를 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증번호가 이메일로 전송됨"),
            @ApiResponse(responseCode = "400", description = "아이디와 이메일이 일치하지 않음")
    })
    @PostMapping("/request-auth")
    public ResponseEntity<String> requestAuthCode(
            @RequestBody(description = "아이디와 이메일 정보") @org.springframework.web.bind.annotation.RequestBody VerifyDTO request) {

        boolean isValidUser = findPwService.validateUser(request.getUserId(), request.getEmail());

        if (!isValidUser) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 아이디와 이메일이 일치하지 않습니다.");
        }

        findPwService.sendAuthCode(request.getUserId(), request.getEmail());
        return ResponseEntity.ok("📩 인증번호가 이메일로 전송되었습니다.");
    }

    // ✅ 입력한 인증번호 검증
    @Operation(summary = "비밀번호 찾기 - 인증번호 검증", description = "입력한 인증번호를 검증하여 유효한지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 성공 여부 반환"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인증번호")
    })
    @PostMapping("/verify-auth")
    public ResponseEntity<Map<String, Boolean>> verifyAuthCode(
            @RequestBody(description = "이메일 및 인증번호 정보") @org.springframework.web.bind.annotation.RequestBody VerifyDTO request) {
        boolean isVerified = findPwService.verifyAuthCode(request.getEmail(), request.getAuthenticationNumber());
        return ResponseEntity.ok(Collections.singletonMap("verified", isVerified));
    }

    // ✅ 새 비밀번호 업데이트
    @Operation(summary = "비밀번호 변경", description = "새로운 비밀번호로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 변경 실패")
    })
    @PostMapping("/update")
    public ResponseEntity<String> updatePassword(@RequestParam String userId,
                                                 @RequestParam String userPass) {

        // ✅ 요청 데이터가 null인지 확인
        if (userId == null || userPass == null || userId.isEmpty() || userPass.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 요청 데이터가 올바르지 않습니다.");
        }

        // ✅ 비밀번호 변경 서비스 호출
        boolean updated = findPwService.updateUserPassword(userId, userPass);

        return updated
                ? ResponseEntity.ok("✅ 비밀번호 변경 완료")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ 비밀번호 변경 실패");
    }
}
