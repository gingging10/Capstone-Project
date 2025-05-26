package com.example.capstone.controller;

import com.example.capstone.dto.EmailSendRequest;
import com.example.capstone.dto.EmailVerifyRequest;
import com.example.capstone.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService verificationService;

    /**
     * 인증번호 전송
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EmailSendRequest request) {
        try {
            verificationService.sendCode(request.getEmail());
            return ResponseEntity.ok("인증번호가 발송되었습니다.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body("x " + e.getMessage());
        }
    }

    /**
     * 인증번호 확인
     */
    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestBody EmailVerifyRequest request) {
        try {
            verificationService.verifyCode(request.getEmail(), request.getCode());
            return ResponseEntity.ok("인증이 완료되었습니다.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body("x " + e.getMessage());
        }
    }
}
