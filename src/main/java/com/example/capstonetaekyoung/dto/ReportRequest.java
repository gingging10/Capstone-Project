package com.example.capstonetaekyoung.dto;

import lombok.*;

@Getter @Setter

public class ReportRequest {

    private String reporterId;
    private String targetId;
    private String reason;
}
