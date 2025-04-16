package com.example.capstonetaekyoung.service;

import com.example.capstonetaekyoung.domain.Member;
import com.example.capstonetaekyoung.domain.Report;
import com.example.capstonetaekyoung.dto.ReportRequest;
import com.example.capstonetaekyoung.repository.MemberRepository;
import com.example.capstonetaekyoung.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public void report(ReportRequest request) {
        // 신고 저장
        Report report = Report.builder()
                .reporterId(request.getReporterId())
                .targetId(request.getTargetId())
                .reason(request.getReason())
                .createdAt(LocalDateTime.now())
                .build();

        reportRepository.save(report);

        // 신고 대상 회원 불러오기
        Member target = memberRepository.findById(request.getTargetId())
                .orElseThrow(() -> new RuntimeException("신고 대상 없음"));

        // 누적 신고 수 체크
        long count = reportRepository.countByTargetId(target.getId());

        if (count >= 3 && target.getStatus() != 0) {
            target.setStatus(0);  // 정지 상태로 변경
            memberRepository.save(target);
        }
    }
}
