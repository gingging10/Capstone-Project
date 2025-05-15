package com.example.capstone.repository;

import com.example.capstone.domain.Join;
import com.example.capstone.domain.JoinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinRepository extends JpaRepository<Join, JoinId> {

    boolean existsByPartyIdAndMemberId(Long partyId, String memberId);

    List<Join> findAllByMemberId(String memberId); // 오류나는 부분 이렇게 수정

    @Modifying
    @Query("DELETE FROM com.example.capstone.domain.Join j WHERE j.partyId IN :partyIds")
    void deleteByPartyIds(@Param("partyIds") List<Long> partyIds);

}
