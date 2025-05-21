package com.example.capstone.config;

import com.example.capstone.auth.CustomOAuth2UserService;
import com.example.capstone.auth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // oAutj2 사용자 정보 서비스 DI
    private final CustomOAuth2UserService customOAuth2UserService;
    // OAuth2 로그인 성공 핸들러 DI
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
     // 필터 DI 주입 필요
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 비밀번호 암호화에 사용할 passwordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // spring security 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 보호 비활성화 (API 서버에서는 CSRF 보호가 필요 없음)
            .csrf(csrf -> csrf.disable())
            // URL별 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 회원가입, 로그인, 정적 리소스 등은 인증 없이 접근 허용
                .requestMatchers(
                    "/signup", "/signup/**",  // 회원가입 폼(HTML) 접근 허용
                    "/api/members/**",                    // 회원가입 API 접근 허용
                    "/members/**",
                    "/oauth2/**",
                    "/login", "/login/**",                // 커스텀 로그인 폼 접근 허용
                    "/api/admin/**",                      // Admin은 별도 로그인 처리 → 인증 제외
                    "/error",
                    "/css/**", "/js/**", "/images/**", "/favicon.ico", // 정적 리소스 허용
                    "/mypage", "/mypage/**" // 마이페이지 접근 허용
                ).permitAll()
                // JWT 인증이 필요한 내부 API 
                .requestMatchers("/api/internal/**").authenticated() // JWT 전용 영역
                .anyRequest().authenticated() // 그 외는 OAuth2 인증
            )
            // localhost:8080/login으로 접근 시 구글 로그인으로 바로 돼서 주석처리함.
            // .formLogin(form -> form
            //     .loginPage("/login")
            //     .permitAll()
            // )

            // localhost:8080/login으로 접근
            // .oauth2Login()을 사용하여 OAuth2 로그인 설정 
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login") // 커스텀 로그인 페이지 사용
                .defaultSuccessUrl("/login/success", true) // 로그인 성공 시
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)) // 사용자 정보 서비스
                .successHandler(oAuth2SuccessHandler) // 로그인 성공 핸들러
            )
            // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 최종적으로 SecurityFilterChain 반환
        return http.build();
    }
}