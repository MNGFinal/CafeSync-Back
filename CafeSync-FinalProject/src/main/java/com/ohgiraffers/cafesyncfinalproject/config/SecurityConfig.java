package com.ohgiraffers.cafesyncfinalproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // ✅ 비밀번호 암호화 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ UserDetailsService 기반 인증 관리자 설정
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 암호화 적용

        return new ProviderManager(List.of(authProvider)); // ProviderManager에 설정
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                                // ✅ Swagger UI 관련 경로 허용
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-resources/**",
                                        "/webjars/**"
                                ).permitAll()

                                // ✅ 로그인 & 회원가입은 누구나 접근 가능
                                .requestMatchers("/api/login", "/api/register", "/api/find-id/**", "/api/find-pass/**", "/api/fran/**").permitAll()

                                // ✅ 권한 설정
                                .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                                .requestMatchers("/api/user/**").hasRole("USER") // 사용자만 접근 가능
//                        .requestMatchers("/api/fran/**").hasAnyRole("ADMIN", "USER") // ADMIN, USER 둘 다 접근 가능
                                .requestMatchers("/api/hq/**").hasRole("ADMIN") // 본사는 ADMIN만 접근 가능

                                // ✅ 나머지 모든 요청은 로그인해야 접근 가능
                                .anyRequest().authenticated()
                )
                // ✅ JWT 필터 등록
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class);

                        .anyRequest().permitAll() // 🔥 모든 요청을 허용 (임시)
                );


        return http.build();
    }
}
