package com.example.capstonetaekyoung.service;

import com.example.capstonetaekyoung.domain.*;
import com.example.capstonetaekyoung.dto.MyPageResponse;
import com.example.capstonetaekyoung.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .nickname(member.getNickname())
                .schoolCode(member.getSchoolCode())
                .schoolDepartment(member.getSchoolDepartment())
                .phoneNum(member.getPhoneNum())
                .joinedParties(joinedParties)
                .build();
    }
}
