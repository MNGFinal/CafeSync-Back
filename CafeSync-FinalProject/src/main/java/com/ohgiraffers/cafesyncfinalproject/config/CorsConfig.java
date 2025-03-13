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

        // ✅ 프론트엔드에서 온 요청 허용 (WebSocket 포함)
        config.setAllowedOriginPatterns(List.of("cafesync-front-production.up.railway.app", "http://127.0.0.1:3000"));

        // ✅ 쿠키, 인증 정보 포함 허용
        config.setAllowCredentials(true);

        // ✅ 허용할 HTTP 메서드 지정 (WebSocket handshake인 OPTIONS 포함)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ WebSocket 요청 헤더도 허용 (Authorization 포함)
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // ✅ 응답 헤더 노출 허용 (Authorization 포함)
        config.setExposedHeaders(List.of("Authorization"));

        // ✅ WebSocket 엔드포인트에도 CORS 적용
        source.registerCorsConfiguration("/ws/**", config);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
