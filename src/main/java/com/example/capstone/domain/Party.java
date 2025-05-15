package com.example.capstone.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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
}
