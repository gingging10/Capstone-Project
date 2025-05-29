package com.example.capstonetaekyoung.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PartyUpdateRequest {
    private String title;
    private String category;
    private int maxMembers;
    private int cost;
    private String chatLink;
    private LocalDateTime deadline;
}

