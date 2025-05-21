package com.example.capstone.repository;

import com.example.capstone.domain.Join;
import com.example.capstone.domain.JoinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinRepository extends JpaRepository<Join, JoinId> {

    boolean existsByPartyIdAndMemberId(Long partyId, String memberId);

    List<Join> findAllByMemberId(String memberId);

    // 페이징 지원: 참여중인 파티 목록 일부만 가져오기
    @Query("SELECT j FROM Join j WHERE j.memberId = :memberId ORDER BY j.id.partyId DESC")
    List<Join> findAllByMemberIdWithPaging(@Param("memberId") String memberId, org.springframework.data.domain.Pageable pageable);

    // 참여중인 파티 개수
    @Query("SELECT COUNT(j) FROM Join j WHERE j.memberId = :memberId")
    int countByMemberId(@Param("memberId") String memberId);
}