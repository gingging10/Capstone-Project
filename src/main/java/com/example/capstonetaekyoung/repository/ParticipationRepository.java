package com.example.capstonetaekyoung.repository;

import com.example.capstonetaekyoung.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByPartyIdAndMemberId(Long partyId, Long memberId);
}
