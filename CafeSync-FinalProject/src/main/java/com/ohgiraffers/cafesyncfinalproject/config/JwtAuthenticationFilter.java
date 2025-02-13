package com.ohgiraffers.cafesyncfinalproject.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        System.out.println("🔍 [JWT 필터] 요청 감지 - Authorization 헤더: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("🔍 [JWT 필터] 추출된 토큰: " + token);

            try {
                String username = jwtUtil.getUsernameFromToken(token);
                System.out.println("🔍 [JWT 필터] 토큰에서 추출한 유저명: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("🔍 [JWT 필터] 로드된 유저 정보: " + userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("✅ [JWT 필터] 인증 성공: " + username);
                } else {
                    System.out.println("⚠️ [JWT 필터] 인증 실패: SecurityContext에 이미 인증 정보가 있음");
                }
            } catch (Exception e) {
                System.out.println("❌ [JWT 필터] JWT 검증 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ [JWT 필터] Authorization 헤더가 없거나 잘못됨");
        }

        filterChain.doFilter(request, response);
    }

}
