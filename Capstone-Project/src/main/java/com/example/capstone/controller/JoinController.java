package com.example.capstone.controller;

import com.example.capstone.domain.Join;
import com.example.capstone.dto.JoinRequest;
import com.example.capstone.service.JoinService;
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
