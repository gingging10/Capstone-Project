package com.example.capstonetaekyoung.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "school_code")
    private String schoolCode;

    @Column(name = "school_department")
    private String schoolDepartment;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer status;

    // ✅ 수동 생성자 추가
    public Member() {}

    public Member(String id, String nickname, String schoolCode, String schoolDepartment, String phoneNum, Integer status) {
        this.id = id;
        this.nickname = nickname;
        this.schoolCode = schoolCode;
        this.schoolDepartment = schoolDepartment;
        this.phoneNum = phoneNum;
        this.status = status;
    }

    // ✅ getter/setter 수동 생성 (필요 시)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getSchoolCode() { return schoolCode; }
    public void setSchoolCode(String schoolCode) { this.schoolCode = schoolCode; }

    public String getSchoolDepartment() { return schoolDepartment; }
    public void setSchoolDepartment(String schoolDepartment) { this.schoolDepartment = schoolDepartment; }

    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
