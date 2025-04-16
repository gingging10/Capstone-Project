package com.example.capstonetaekyoung.controller;

import com.example.capstonetaekyoung.dto.MyPageResponse;
import com.example.capstonetaekyoung.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/{id}")
    public ResponseEntity<MyPageResponse> getMyPage(@PathVariable String id) {
        return ResponseEntity.ok(myPageService.getMyPage(id));
    }
}
