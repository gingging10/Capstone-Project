package com.example.capstone.service;

import com.example.capstone.domain.Member;
import com.example.capstone.domain.MemberStatus;
import com.example.capstone.dto.SignUpRequestDto;
import com.example.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signUp(SignUpRequestDto dto) {
        Member member = Member.builder()
                .id(dto.getId())
                .name(dto.getName())
                .schoolCode(dto.getSchoolCode())
                .schoolDepartment(dto.getSchoolDepartment())
                .phoneNum(dto.getPhoneNum())
                .email(dto.getEmail())
                .status(MemberStatus.NORMAL)
                .build();

        return memberRepository.save(member);
    }
}