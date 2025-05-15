package com.example.capstone.config;

import com.example.capstone.auth.CustomOAuth2UserService;
import com.example.capstone.auth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


// Spring Security 설정 클래스
// OAuth2 로그인 설정
// 접근 권한 설정

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService; // OAuth 로그인 시 사용자 정보 처리 커스터마이징 클래스
    private final OAuth2SuccessHandler oAuth2SuccessHandler; // 로그인 성공 후 커스텀 로직 실행


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/members/**",   // API 정보 조회 허용
                                "/members/**",       // 회원가입
                                "/oauth2/**",        // 로그인 redirect
                                "/login/**",         // 로그인
                                "/error"             // 에러
                        ).permitAll()
                        .anyRequest().authenticated() // 그 외는 인증 필요하게
                )

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        // 사용자 정보 처리 서비스 등록
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        // 로그인 성공 후 처리 핸들러 등록
                        .successHandler(oAuth2SuccessHandler)
                );

        return http.build();  // 설정된 SecurityFilterChain 변환
    }
}