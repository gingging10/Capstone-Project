package com.example.capstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 👉 Postman 테스트를 위해 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/members").permitAll() // 👉 회원 등록은 인증 없이 허용
                        .anyRequest().permitAll()                // 👉 전체 허용 (개발 중이니까!)
                )
                .httpBasic(Customizer.withDefaults()); // 👉 기본 인증 방식을 사용 (JWT 붙일 땐 제거 예정)

        return http.build();
    }
}
