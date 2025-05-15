package com.example.capstone.dto;

import com.example.capstone.domain.Member;
import lombok.Getter;

// 클라에 회원정보를 전달하는 응답 DTO
// member 엔티티 객체를 기반으로 필요한 필드만 추출해 전달
@Getter
public class MemberResponseDto {
    private final String id; // 회원 고유 ID
    private final String name;  // 이름
    private final String schoolCode;  // 학번
    private final String schoolDepartment; // 학과
    private final String phoneNum; // 전화번호
    private final String email; // 학교 이메일

    public MemberResponseDto(Member m) {
        this.id = m.getId();
        this.name = m.getName();
        this.schoolCode = m.getSchoolCode();
        this.schoolDepartment = m.getSchoolDepartment();
        this.phoneNum = m.getPhoneNum();
        this.email = m.getEmail();
    }
}