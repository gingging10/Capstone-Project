package com.example.capstonetaekyoung.controller;

import com.example.capstonetaekyoung.domain.Join;
import com.example.capstonetaekyoung.dto.JoinRequest;
import com.example.capstonetaekyoung.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/join")
@RequiredArgsConstructor

public class JoinController {

    private final JoinService joinService;

    @PostMapping
    public ResponseEntity<Join> joinParty(@RequestBody JoinRequest request) {
        return ResponseEntity.ok(joinService.joinParty(request));
    }
}
