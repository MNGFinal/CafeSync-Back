package com.ohgiraffers.cafesyncfinalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // ✅ 프론트엔드에서 온 요청 허용 (특정 도메인 or 모든 요청 허용)
        config.setAllowedOriginPatterns(List.of("http://localhost:3000", "http://127.0.0.1:3000"));

        // ✅ 쿠키, 인증 정보 포함 허용
        config.setAllowCredentials(true);

        // ✅ 허용할 HTTP 메서드 지정
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ 모든 요청 헤더 허용 (Authorization 포함)
        config.setAllowedHeaders(List.of("*"));

        // ✅ 응답 헤더 노출 허용 (Authorization 포함)
        config.setExposedHeaders(List.of("*"));

        // ✅ 모든 경로에 대해 CORS 적용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
