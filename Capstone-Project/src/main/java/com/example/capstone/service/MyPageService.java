package com.example.capstone.service;

import com.example.capstone.domain.*;
import com.example.capstone.dto.MyPageResponse;
import com.example.capstone.dto.PartyListDto;
import com.example.capstone.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final JoinRepository joinRepository;

    public MyPageResponse getMyPage(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        List<Join> joins = joinRepository.findAllByMemberId(memberId);
        List<Party> joinedParties = joins.stream()
                .map(Join::getParty)
                .toList();

        return MyPageResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .schoolCode(member.getSchoolCode())
                .schoolDepartment(member.getSchoolDepartment())
                .phoneNum(member.getPhoneNum())
                .joinedParties(joinedParties)
                .build();
    }

    public void updateMyPage(MyPageResponse myPage) {
        Member member = memberRepository.findById(myPage.getId())
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        member.setName(myPage.getName());
        member.setPhoneNum(myPage.getPhoneNum());
        member.setSchoolCode(myPage.getSchoolCode());
        member.setSchoolDepartment(myPage.getSchoolDepartment());
        memberRepository.save(member);
    }

    // 참여중인 파티 페이징 (PartyListDto로 변환)
    public List<PartyListDto> getMyParties(String memberId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<Join> joins = joinRepository.findAllByMemberIdWithPaging(memberId, pageable);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return joins.stream()
                .map(Join::getParty)
                .map(party -> PartyListDto.builder()
                        .title(party.getTitle())
                        .deadline(party.getDeadline() != null ? party.getDeadline().format(formatter) : "")
                        .build())
                .collect(Collectors.toList());
    }

    public int getMyPartiesCount(String memberId) {
        return joinRepository.countByMemberId(memberId);
    }
}