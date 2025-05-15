package com.example.capstone.auth;

import com.example.capstone.auth.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 소셜 로그인 성공 시 JWT 토큰을 발급하는 핸들러

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // OAuth2 인증 성공 → Authentication 객체에서 사용자 정보 추출
        String userId = authentication.getName(); // 기본적으로 사용자명 반환

        // 권한 설정 (예: ROLE_USER)
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("ROLE_USER");

        // JWT 생성용 Claims 생성
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userId);
        claims.put("role", role);

        // Access Token 생성
        String accessToken = jwtProvider.getAccesToken(claims);

        // JSON 형태로 응답 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("{\"accessToken\": \"" + accessToken + "\"}");

        // 콘솔 로그 확인용 출력
        System.out.println("[OAuth2 로그인 성공] userId = " + userId + ", role = " + role);
        System.out.println("[발급된 Access Token] " + accessToken);
    }
}
