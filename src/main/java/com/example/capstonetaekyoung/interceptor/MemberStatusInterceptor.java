package com.example.capstonetaekyoung.interceptor;

import com.example.capstonetaekyoung.domain.Member;
import com.example.capstonetaekyoung.repository.MemberRepository;
import com.example.capstonetaekyoung.auth.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MemberStatusInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }

        String token = authHeader.substring(7);
        String memberId = jwtUtil.getMemberIdFromToken(token);

        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null && member.getStatus() == 0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("정지된 회원입니다.");
            return false;
        }

        return true;
    }
}
