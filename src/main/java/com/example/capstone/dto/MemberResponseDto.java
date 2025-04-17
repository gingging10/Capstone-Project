package com.example.capstone.dto;

import com.example.capstone.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private final String id;
    private final String name;
    private final String schoolCode;
    private final String schoolDepartment;
    private final String phoneNum;
    private final String email;

    public MemberResponseDto(Member m) {
        this.id = m.getId();
        this.name = m.getName();
        this.schoolCode = m.getSchoolCode();
        this.schoolDepartment = m.getSchoolDepartment();
        this.phoneNum = m.getPhoneNum();
        this.email = m.getEmail();
    }
}