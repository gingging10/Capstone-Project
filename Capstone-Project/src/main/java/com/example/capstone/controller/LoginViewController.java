package com.example.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login/login";  // templates/login/login.html 경로로 이동
    }
}
