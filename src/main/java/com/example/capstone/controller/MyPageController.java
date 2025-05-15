package com.example.capstone.controller;

import com.example.capstone.dto.MyPageResponse;
import com.example.capstone.service.MyPageService;
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
