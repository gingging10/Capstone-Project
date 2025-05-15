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

    public Member updateMember(String id, SignUpRequestDto dto) { //회원정보 수정
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원이 존재하지 않습니다."));

        member.setName(dto.getName());
        member.setSchoolCode(dto.getSchoolCode());
        member.setSchoolDepartment(dto.getSchoolDepartment());
        member.setPhoneNum(dto.getPhoneNum());
        member.setEmail(dto.getEmail());

        return memberRepository.save(member);

    }
}