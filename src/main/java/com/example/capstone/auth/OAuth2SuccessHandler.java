package com.example.capstone.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인 성공 시 동작하는 핸들러 인터페이스

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // OAuth 인증이 성공되면 여기로 들어옴
        String userId = authentication.getName(); // 소셜 로그인한 유저 ID

        // JWT 토큰 발급
        String token = jwtProvider.createToken(userId);

        // 응답에 JSON 형태로 토큰 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}"); // 토큰을 형태 변환
    }
}