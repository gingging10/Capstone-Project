package com.example.capstone.dto;

import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PartyListDto {
    private String title;
    private String deadline; // String 타입으로 포맷된 날짜
}