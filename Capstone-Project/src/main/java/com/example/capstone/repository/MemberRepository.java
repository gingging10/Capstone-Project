package com.example.capstone.repository;

import com.example.capstone.domain.Member;
import com.example.capstone.domain.MemberStatus;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    // 기본적인 CRUD 메서드는 이미 포함되어 있음
    // 추가로 필요한 메서드는 여기에 정의 가능

    Page<Member> findByStatusIn(List<MemberStatus> statuses, Pageable pageable);



    //회원조회용도
    @Query("SELECT COUNT(m) FROM Member m")
    long countAllMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.status <> 0")
    long countActiveMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.status = 1")
    long countNormalMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.status = 2")
    long countVerifiedMembers();
}