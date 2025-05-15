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

    // 회원 레포
    private final MemberRepository memberRepository;

    // 회원가입 처리
    public Member signUp(SignUpRequestDto dto) {
        Member member = Member.builder()
                .id(dto.getId()) // 사용자 고유 ID
                .name(dto.getName()) // 이름
                .schoolCode(dto.getSchoolCode()) // 학번
                .schoolDepartment(dto.getSchoolDepartment()) // 학과
                .phoneNum(dto.getPhoneNum()) // 전화번호
                .email(dto.getEmail())  // 학교 이메일
                .status(MemberStatus.NORMAL) // 상태 기본값 nomal
                .build();

        return memberRepository.save(member); // DB에 저장
    }

    // 회원정보 수정
    // 주어진 ID에 해당하는 회원정보를 찾아 DTO의 값으로 업데이트
    public Member updateMember(String id, SignUpRequestDto dto) { //회원정보 수정
        // ID로 회원을 조회하고 없으면 예외 발생
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원이 존재하지 않습니다."));

        // 필드 수정
        member.setName(dto.getName());
        member.setSchoolCode(dto.getSchoolCode());
        member.setSchoolDepartment(dto.getSchoolDepartment());
        member.setPhoneNum(dto.getPhoneNum());
        member.setEmail(dto.getEmail());

        return memberRepository.save(member); // 수정된 내용 DB 저장

    }
}