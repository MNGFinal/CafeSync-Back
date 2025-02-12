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

        // ✅ 쿠키를 포함한 요청 허용
        config.setAllowCredentials(true);

        // ✅ 프론트엔드 도메인만 허용 (setAllowedOrigins 사용)
        config.setAllowedOrigins(List.of("http://localhost:3000"));

        // ✅ 허용할 HTTP 메서드 지정
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ 허용할 요청 헤더 지정 (Authorization 포함)
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"));

        // ✅ 응답 헤더 노출 허용 (쿠키 사용 시 필요)
        config.setExposedHeaders(List.of("Authorization"));

        // ✅ 모든 경로에 대해 CORS 적용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
