package com.example.capstone.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    // 기존 자동 생성 키
    // private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // application.yml 또는 .env에서 설정된 JWT 비밀 키를 주입받음
    @Value("${jwt.secret}")
    private String secret;

    // 토큰 유효 시간 (3시간)
    private final long validityInMilliseconds = 1000L * 60 * 60 * 3;

     // 토큰 서명에 사용할 Key 객체 생성
     // secret 값을 바이트 배열로 변환해서 키로 사용
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // sub: userId 저장

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds); // 유효 시간 3시간

        return Jwts.builder()
                .setClaims(claims)           // 사용자 정보
                .setIssuedAt(now)            // 토큰 발급 시간
                .setExpiration(expiration)   // 만료 시간
                .signWith(getSigningKey())               // 서명 비밀 키
                .compact();                  // 토큰 생성
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody()
                .getSubject(); // sub 필드 값 반환
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // 유효하지않으면 예외 발생
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 만료, 위조, 잘못된 형식일 경우 false 반환
        }
    }
}