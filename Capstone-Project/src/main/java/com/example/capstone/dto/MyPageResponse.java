package com.example.capstone.dto;

import com.example.capstone.domain.Party;
import lombok.*;

import java.util.List;

@Getter @Setter @Builder
public class MyPageResponse {
    private String id;
    private String name;
    private String schoolCode;
    private String schoolDepartment;
    private String phoneNum;

    private List<Party> joinedParties;
}
