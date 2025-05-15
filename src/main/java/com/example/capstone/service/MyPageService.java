package com.example.capstone.service;

import com.example.capstone.domain.*;
import com.example.capstone.dto.MyPageResponse;
import com.example.capstone.repository.*;
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
                .name(member.getName())
                .schoolCode(member.getSchoolCode())
                .schoolDepartment(member.getSchoolDepartment())
                .phoneNum(member.getPhoneNum())
                .joinedParties(joinedParties)
                .build();
    }
}
