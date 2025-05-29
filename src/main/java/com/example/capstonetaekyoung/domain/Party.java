package com.example.capstonetaekyoung.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "party")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    private int maxMembers;

    private int cost;

    private LocalDate deadline;

    @Column(name = "chat_link")
    private String chatLink;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Member creator;

    public boolean canJoin() {
    }

    public Arrays getParticipants() {
    }

    public void setClosed(boolean b) {
    }

    public boolean isClosed() {
    }
}
