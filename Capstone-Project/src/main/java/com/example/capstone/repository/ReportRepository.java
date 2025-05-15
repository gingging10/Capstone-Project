package com.example.capstone.repository;

import com.example.capstone.domain.Report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByTargetId(String targetId);  // 누적 신고 수 확인

      void deleteByTargetIdIn(List<String> targetIds);
}
