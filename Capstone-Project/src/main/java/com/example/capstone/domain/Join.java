package com.example.capstone.domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "joining")
@IdClass(JoinId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Join {
    @Id
    private Long partyId;

    @Id
    private String memberId;

    @ManyToOne
    @JoinColumn(name = "partyId", insertable = false, updatable = false)
    private Party party;

    @ManyToOne
    @JoinColumn(name = "memberId", insertable = false, updatable = false)
    private Member member;
}
