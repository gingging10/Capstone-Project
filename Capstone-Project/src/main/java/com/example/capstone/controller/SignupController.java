package com.example.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

@Controller
public class SignupController {

    // 회원가입 페이지 GET 요청 처리
    @GetMapping("/signup")
    public String signupPage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        String email = "";
        // OAuth2 인증 정보가 있으면 이메일 추출
        if (principal != null && principal.getAttribute("email") != null) {
            email = principal.getAttribute("email");
        }
        // 뷰에 이메일 정보 전달(자동 입력용)
        model.addAttribute("email", email);
        // 회원가입 페이지로 이동
        return "signup/signup";
    }
}