package com.example.capstone.dto;

import com.example.capstone.domain.Admin;
import lombok.Getter;

@Getter
public class AdminResponseDto {
    private final String ad_id;
    private final String ad_pw;
    private final String ad_email;

    public AdminResponseDto(Admin m) {
        this.ad_id = m.getAd_id();
        this.ad_pw = m.getAd_pw();
        this.ad_email = m.getAd_email();

    }
}