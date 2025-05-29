package com.example.capstonetaekyoung.security;

import com.example.capstonetaekyoung.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 필요 시 ROLE 반환 (ex: ROLE_USER)
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return member.getPassword(); // 또는 null (소셜 로그인이라면)
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // 로그인 ID로 이메일 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getStatus() != 0; // 정지 상태면 false
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.getStatus() != 0;
    }

    public String getId() {
        return member.getId();
    }
}
