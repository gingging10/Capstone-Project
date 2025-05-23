package com.example.capstone.repository;

import com.example.capstone.domain.Join;
import com.example.capstone.domain.JoinId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRepository extends JpaRepository<Join, JoinId> {

    boolean existsByPartyIdAndMemberId(Long partyId, String memberId);

    List<Join> findAllByMemberId(String memberId); // 🔥 오류나는 부분 이렇게 수정
}
