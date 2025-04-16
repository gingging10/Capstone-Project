package com.example.capstonetaekyoung.repository;

import com.example.capstonetaekyoung.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
