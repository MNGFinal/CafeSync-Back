package com.ohgiraffers.cafesyncfinalproject.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret:default-secret-key-should-be-changed}") // ✅ 환경 변수 없을 경우 기본값 설정
    private String secretKey;

    private Key SECRET_KEY;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ JWT Secret Key가 설정되지 않았습니다!");
        }
        System.out.println("🔐 Loaded JWT Secret Key: " + secretKey); // ✅ 확인용 로그
        SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getSignKey() {
        return SECRET_KEY;
    }

    // ✅ JWT Access & Refresh Token 생성
    public String generateToken(String username, long expirationTime) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ JWT 토큰에서 사용자 정보 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ JWT 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // ✅ 만료된 경우 true 반환
        }
    }

    // ✅ JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getTokenExpiration(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            long expirationTime = claims.getExpiration().getTime();
            long currentTime = System.currentTimeMillis();

            System.out.println("🔍 Access Token 만료 시간: " + new Date(expirationTime));
            System.out.println("⏳ 남은 시간: " + ((expirationTime - currentTime) / 1000) + "초");

            return expirationTime - currentTime; // 남은 시간(ms)
        } catch (Exception e) {
            System.out.println("🚨 토큰 검증 실패: " + e.getMessage());
            return -1;
        }
    }

}
