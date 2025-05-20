package com.example.capstone.auth;

import com.example.capstone.auth.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 소셜 로그인 성공 시 JWT 토큰을 발급하고, 신규/기존 회원에 따라 리다이렉트하는 핸들러

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    // 실제로는 UserService 등에서 DB 조회로 구현해야 함
    // @Autowired private final MemberService memberService;

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
        claims.put("username", userId); // JWT에 username 저장
        claims.put("role", role); // JWT에 role 저장

        // Access Token 생성
        String accessToken = jwtProvider.getAccesToken(claims);

        // 신규 회원 여부 판단 (예시: DB에서 userId로 회원 존재 여부 확인)
        boolean isNewUser = checkIfNewUser(userId); // 실제 구현 필요

        // JWT를 쿠키에 저장 (선택, 필요시)
        // Cookie cookie = new Cookie("accessToken", accessToken);
        // cookie.setPath("/");
        // cookie.setHttpOnly(true);
        // response.addCookie(cookie);

        // 회원상태에 따른 리다이렉트 처리
        if (isNewUser) {
            // 신규 회원이면 /signup으로 리다이렉트
            response.sendRedirect("/signup");
        } else {
            // 기존 회원이면 메인 페이지 등으로 리다이렉트
            response.sendRedirect("/");
        }

        // 콘솔 로그 확인용 출력
        System.out.println("[OAuth2 로그인 성공] userId = " + userId + ", role = " + role);
        System.out.println("[발급된 Access Token] " + accessToken);
    }

    // 신규 회원 여부 확인 메서드 (예시)
    private boolean checkIfNewUser(String userId) {
        // TODO: DB에서 userId로 회원 조회 후 없으면 true 반환
        // 예시: return !memberService.existsByUserId(userId);
        return true; // 임시로 항상 신규 회원 처리
    }
}