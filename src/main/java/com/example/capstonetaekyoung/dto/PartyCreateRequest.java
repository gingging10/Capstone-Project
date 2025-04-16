package com.example.capstonetaekyoung.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter

public class PartyCreateRequest {
    private String title;
    private String category;
    private int maxMembers;
    private int cost;
    private LocalDate deadline;
    private String chatLink;
    private String creatorId;
}
