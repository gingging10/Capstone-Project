package com.example.capstonetaekyoung.service;


import com.example.capstonetaekyoung.domain.*;
import com.example.capstonetaekyoung.dto.JoinRequest;
import com.example.capstonetaekyoung.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class JoinService {

    private final JoinRepository joinRepository;
    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;

    public Join joinParty(JoinRequest request) {
        if (joinRepository.existsByPartyIdAndMemberId(request.getPartyId(), request.getMemberId())) {
            throw new RuntimeException("이미 참여한 파티입니다.");
        }

        Party party = partyRepository.findById(request.getPartyId())
                .orElseThrow(() -> new RuntimeException("파티 없음"));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Join join = Join.builder()
                .partyId(party.getId())
                .memberId(member.getId())
                .party(party)
                .member(member)
                .build();

        return joinRepository.save(join);
    }
}
