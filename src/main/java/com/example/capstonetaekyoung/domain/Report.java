package com.example.capstonetaekyoung.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reporterId;
    private String targetId;
    private String reason;
    private LocalDateTime createdAt;
}
