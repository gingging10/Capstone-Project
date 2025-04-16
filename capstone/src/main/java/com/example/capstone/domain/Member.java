package com.example.capstone.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members") // members 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private String id;  // 유저 ID (소셜 고유 ID or 학번)

    @Column(nullable = false) // 이름
    private String name;

    @Column(name = "school_code") // 학번
    private String schoolCode;

    @Column(name = "school_department") // 학과
    private String schoolDepartment;

    @Column(name = "phone_num", nullable = false) // 전화번호
    private String phoneNum;

    @Column(columnDefinition = "INT DEFAULT 1") // 상태
    private Integer status;
}