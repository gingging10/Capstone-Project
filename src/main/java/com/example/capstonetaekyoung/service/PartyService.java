package com.example.capstonetaekyoung.service;

import com.example.capstonetaekyoung.domain.Member;
import com.example.capstonetaekyoung.domain.Party;
import com.example.capstonetaekyoung.domain.Participation;
import com.example.capstonetaekyoung.dto.PartyCreateRequest;
import com.example.capstonetaekyoung.dto.PartyUpdateRequest;
import com.example.capstonetaekyoung.repository.MemberRepository;
import com.example.capstonetaekyoung.repository.PartyRepository;
import com.example.capstonetaekyoung.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
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
                .clone()
                .build();

        return partyRepository.save(party);
    }

    public List<Party> getAllParties(String category) {
        return (category == null || category.isEmpty())
                ? partyRepository.findAll()
                : partyRepository.findByCategory(category);
    }

    public Party getPartyById(Long id) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("파티를 찾을 수 없습니다."));
    }

    @Transactional
    public void joinParty(Long partyId, Long memberId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("파티 없음"));
        if (!party.canJoin()) {
            throw new IllegalStateException("정원이 초과되었거나 모집이 마감된 파티입니다.");
        }

        Member member = memberRepository.findById(String.valueOf(memberId))
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        if (participationRepository.findByPartyIdAndMemberId(partyId, memberId).isPresent()) {
            throw new IllegalStateException("이미 참여한 파티입니다.");
        }

        Participation participation = new Participation();
        participation.setParty(party);
        participation.setMember(member);
        participationRepository.save(participation);
    }

    @Transactional
    public void leaveParty(Long partyId, Long memberId) {
        Participation participation = participationRepository
                .findByPartyIdAndMemberId(partyId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("참여 기록 없음"));
        participationRepository.delete(participation);
    }

    @Transactional
    public void closeParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("파티 없음"));
        party.setClosed(true);
    }

    public List<Party> filterParties(String category, String keyword) {
        return partyRepository.searchParties(category, keyword);
    }

    public List<Member> getParticipants(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("파티 없음"));
        return party.getParticipants().stream()
                .map(Participation::getMember)
                .toList();
    }

    @Transactional
    public Party updateParty(Long id, PartyUpdateRequest request) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("파티를 찾을 수 없습니다."));

        party.setTitle(request.getTitle());
        party.setCategory(request.getCategory());
        party.setMaxMembers(request.getMaxMembers());
        party.setCost(request.getCost());
        party.setDeadline(LocalDate.from(request.getDeadline()));
        party.setChatLink(request.getChatLink());

        return party;
    }
    
    public <LocalDateTime> List<Party> filterParties(String category, String keyword) {
        List<Party> parties = partyRepository.searchParties(category, keyword);
        LocalDateTime now = LocalDateTime.now();
        parties.forEach(p -> {
            if (!p.isClosed() && p.getDeadline().isBefore((ChronoLocalDate) now)) {
                p.setClosed(true);
            }
        });
        return parties;
    }
}


