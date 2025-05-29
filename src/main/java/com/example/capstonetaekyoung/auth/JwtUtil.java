package com.example.capstonetaekyoung.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdk.internal.org.jline.reader.LineReaderBuilder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String getMemberIdFromToken(String token) {
        LineReaderBuilder Jwts = null;
        // 실제 사용하는 비밀키로 변경
        String secret = "secret_key";
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // subject가 memberId라고 가정
    }
}
