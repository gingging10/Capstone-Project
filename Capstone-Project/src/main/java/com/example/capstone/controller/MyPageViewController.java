package com.example.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageViewController {

    @GetMapping("/mypage")
    public String showMypage() {
        return "mypage/mypage";  // 템플릿 경로: resources/templates/mypage/mypage.html
    }
    @GetMapping("/mypage/my-parties")
    public String showMyParties() {
        return "mypage/my-parties"; // ← 파일 위치: templates/mypage/my-parties.html
    }
}