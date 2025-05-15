package com.example.capstone.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String id; // 구글 ID or 닉네임 느낌
    private String name;
    private String schoolCode;
    private String schoolDepartment;
    private String phoneNum;
    private String email;
}