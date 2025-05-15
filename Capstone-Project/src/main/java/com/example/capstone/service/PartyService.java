package com.example.capstone.service;

import com.example.capstone.domain.*;
import com.example.capstone.dto.PartyCreateRequest;
import com.example.capstone.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;

    public Party createParty(PartyCreateRequest request) {
        Member creator = memberRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Party party = Party.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .maxMembers(request.getMaxMembers())
                .cost(request.getCost())
                .deadline(request.getDeadline())
                .chatLink(request.getChatLink())
                .creator(creator)
                .build();

        return partyRepository.save(party);
    }

    public List<Party> getAllParties(String category) {
        return category == null ? partyRepository.findAll()
                : partyRepository.findByCategory(category);
    }

    public Party getPartyById(Long id) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("파티를 찾을 수 없습니다"));
    }
}
