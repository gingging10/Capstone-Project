package com.example.capstonetaekyoung.dto;

import com.example.capstonetaekyoung.domain.Party;
import lombok.*;

import java.util.List;

@Getter @Setter @Builder
public class MyPageResponse {
    private String id;
    private String nickname;
    private String schoolCode;
    private String schoolDepartment;
    private String phoneNum;

    private List<Party> joinedParties;
}
