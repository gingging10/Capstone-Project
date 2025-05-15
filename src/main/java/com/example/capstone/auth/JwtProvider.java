package com.example.capstone.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    // JWT 서명에 사용할 비밀 키 (자동 생성)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 런타임마다 키가 바뀜
    // 현재는 .env나 application.yml에 비밀키 따로 보관해서 사용중

    // 토큰 유효 시간 (3시간)
    private final long validityInMilliseconds = 1000L * 60 * 60 * 3;

    // JWT 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // sub: userId 저장

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds); // 유효 시간 3시간

        return Jwts.builder()
                .setClaims(claims)           // 사용자 정보
                .setIssuedAt(now)            // 토큰 발급 시간
                .setExpiration(expiration)   // 만료 시간
                .signWith(key)               // 서명 비밀 키
                .compact();                  // 토큰 생성
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)  // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody()
                .getSubject(); // sub 필드 값 반환
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 유효하지않으면 예외 발생
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 만료, 위조, 잘못된 형식일 경우 false 반환
        }
    }
}