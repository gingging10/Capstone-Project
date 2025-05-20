package com.example.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginPage() {
        return "login/Login"; // templates/login/Login.html로 이동
    }

    // 로그인 성공 후 메인 홈페이지로 이동
    @GetMapping("/login/success")
    public String loginSuccess() {
        return "party/index"; // 로그인 성공 후 메인 홈페이지로 이동
    }
    
}
