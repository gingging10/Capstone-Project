package com.example.capstone.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin") // admin 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @Column(name = "ad_id", nullable = false)
    private String ad_id;  // 매니저(관리자) ID (매니저 전용 고유 ID)

    @Column(name = "ad_pw", nullable = false)
    private String ad_pw;  // 매니저(관리자) PW (매니저 전용 고유 PW)

    @Column(name = "ad_email") // 매니저(관리자) 이메일
    private String ad_email;

    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private AdminStatus ad_status = AdminStatus.NORMAL;

}