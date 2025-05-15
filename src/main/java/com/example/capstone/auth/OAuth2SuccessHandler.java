package com.example.capstone.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// OAuth2 로그인 성공 시 호출되는 핸들러
// 로그인 성공 후 JWT 토큰을 발급하여 클라이언트에게 반환

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    //jwt 토큰 생성 클래스 주입
    private final JwtProvider jwtProvider;

    //로그인 성공 시 실행되는 메서드 (요청정보, 응답정보, 인증객체)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 소셜 로그인으로 인증된 사용자 ID를 가져옴
        String userId = authentication.getName(); // 소셜 로그인한 유저 ID

        // JWT 토큰 생성
        String token = jwtProvider.createToken(userId);

        // 클라이언트에게 JSON 형태로 토큰 반환
        response.setContentType("application/json"); // 응답 타입 json으로 설정
        response.setCharacterEncoding("utf-8"); // 인코딩 설정
        response.getWriter().write("{\"token\": \"" + token + "\"}"); // 토큰 변환
    }
}