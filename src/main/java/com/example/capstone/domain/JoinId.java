package com.example.capstone.domain;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class JoinId {
    private Long partyId;
    private String memberId;
}
