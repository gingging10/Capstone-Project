package com.example.capstone.auth;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // OAuth2 로그인 성공 시 호출되는 메서드
    // 매개변수: (클라이언트 id, 액세스 토큰)
    // 리턴: 인증된 사용자 정보
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        return super.loadUser(userRequest);
    }
}