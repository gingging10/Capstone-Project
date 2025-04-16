package com.example.capstonetaekyoung.controller;

import com.example.capstonetaekyoung.dto.ReportRequest;
import com.example.capstonetaekyoung.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<String> report(@RequestBody ReportRequest request) {
        reportService.report(request);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }
}
