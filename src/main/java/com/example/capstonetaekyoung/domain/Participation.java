package com.example.capstonetaekyoung.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Party party;

    @Getter
    @ManyToOne
    private Member member;

    public void setParty(Party party) {
    }

    public void setMember(Member member) {
    }


    // getters and setters
}