package com.ohgiraffers.cafesyncfinalproject.account.controller;

import com.ohgiraffers.cafesyncfinalproject.account.model.dto.UserDTO;
import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import com.ohgiraffers.cafesyncfinalproject.account.model.service.UserService;
import com.ohgiraffers.cafesyncfinalproject.config.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "로그인 유저 관련 API")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final StringRedisTemplate redisTemplate; // ✅ Redis 추가

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // ✅ 15분
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // ✅ 7일

    // ✅ 임시 회원가입 API
    @Operation(
            summary = "회원가입",
            description = "사용자가 회원가입을 요청하면, 새로운 계정을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        System.out.println("userDTO = " + userDTO);
        UserDTO savedUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ 로그인 API (세션 방식)
    @Operation(
            summary = "로그인",
            description = "사용자가 아이디와 비밀번호를 입력하여 로그인을 시도합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "401", description = "로그인 실패")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUserId(), userDTO.getUserPass())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtil.generateToken(userDTO.getUserId(), ACCESS_TOKEN_EXPIRATION);
            String refreshToken = jwtUtil.generateToken(userDTO.getUserId(), REFRESH_TOKEN_EXPIRATION);

            redisTemplate.opsForValue().set("refresh:" + userDTO.getUserId(), refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);

            User user = userService.findUserById(userDTO.getUserId());

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("authority", user.getAuthority());
            response.put("jobCode", user.getJobCode());
            response.put("storeCode", user.getStoreCode());

            return ResponseEntity.ok(response); // ✅ 응답 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "로그인 실패"));
        }
    }

    // ✅ Refresh Token을 이용한 Access Token 재발급 (세션 방식)
    @Operation(
            summary = "Access Token 재발급",
            description = "사용자의 Refresh Token을 이용해 새로운 Access Token을 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access Token 재발급 성공"),
                    @ApiResponse(responseCode = "401", description = "Refresh Token이 유효하지 않음")
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestBody Map<String, String> requestBody) {
        String refreshToken = requestBody.get("refreshToken");

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Refresh Token이 필요합니다."));
        }

        // ✅ Refresh Token에서 사용자 ID 추출
        String username = jwtUtil.getUsernameFromToken(refreshToken);

        // ✅ Redis에서 저장된 Refresh Token 확인
        String storedRefreshToken = redisTemplate.opsForValue().get("refresh:" + username);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Refresh Token이 유효하지 않습니다. 다시 로그인하세요."));
        }

        // ✅ 새로운 Access Token 발급 (15분)
        String newAccessToken = jwtUtil.generateToken(username, ACCESS_TOKEN_EXPIRATION);

        // ✅ 새 Access Token을 응답으로 반환
        return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
    }

    // ✅ 현재 로그인된 사용자 정보 반환 (Redux에서 관리할 데이터)
    @Operation(
            summary = "현재 로그인된 사용자 정보 조회",
            description = "Access Token을 이용하여 현재 로그인된 사용자의 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 반환"),
                    @ApiResponse(responseCode = "401", description = "Access Token이 유효하지 않음")
            }
    )
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Access Token이 없습니다."));
        }

        accessToken = accessToken.substring(7); // "Bearer " 제거
        String username = jwtUtil.getUsernameFromToken(accessToken);
        User user = userService.findUserById(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "사용자 정보를 찾을 수 없습니다."));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("authority", user.getAuthority());
        response.put("jobCode", user.getJobCode());
        response.put("storeCode", user.getStoreCode());

        return ResponseEntity.ok(response);
    }
}
