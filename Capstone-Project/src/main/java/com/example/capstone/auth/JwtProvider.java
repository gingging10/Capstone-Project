package com.example.capstone.auth;


import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Collections;

@Component
public class JwtProvider {

    @Value("${custom.jwt.secretKey}")
    private String secretKeyCode;

    @Value("${custom.jwt.access-token-exp}")
    private int accessTokenExp;

    @Value("${custom.jwt.refresh-token-exp}")
    private int refreshTokenExp;

    private SecretKey secretKey;


    public Authentication getAuthentication(String token) {
        Map<String, Object> claims = getClaims(token);
        String username = (String) claims.get("username"); // 또는 "id", "email" 등 claim 키에 맞게
        String role = (String) claims.get("role");         // 토큰에 넣은 role 이름 맞춰야 함
    
        return new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    public SecretKey getSecretKey(){
        if( secretKey == null){
            String encoding = Base64.getEncoder().encodeToString(secretKeyCode.getBytes());
		    secretKey = Keys.hmacShaKeyFor(encoding.getBytes());
        }
        return secretKey;
    }

    //토큰은 문자열로 만들어짐 그래서 return을 String으로 함
    private String genToken(Map<String, Object> map, int seconds){ // 초는 토큰의 유효시간 설정
        //인자 map = 개인정보를 받음
        //현재 날짜를 구하고 시간을 얻어낸다
        long now = new Date().getTime();

        //현재 날짜(시간) 보단 이후
        //토큰 유효시간 생성
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);
        
        //그냥 생성자로 생성해서 만들면 인자들이 뭐가 뭔지 몰라서 빌드 패턴을
        // 쓰는게 가독성이 좋다
        JwtBuilder jwtBuilder = Jwts.builder()
        .setSubject("ljh")  // ← 구버전 호환 방식
        .setExpiration(accessTokenExpiresIn)
        .signWith(getSecretKey());
        
        
        // map 담겨있는 개인정보를 뺴서 set에 담음
        Set<String> keys = map.keySet();

        //속도가 더 빠름 iterator는 커서가 존재해서 바로 찾을수있다
        // 담겨있는 개인정보를 토큰으로 만듦
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            String key = it.next();
            Object value = map.get(key);
            jwtBuilder.claim(key, value);
        }
        //토큰을 만들어서 반환
        return jwtBuilder.signWith(getSecretKey()).compact();
    }

    public String getAccesToken(Map<String, Object> map){
        return genToken(map, accessTokenExp); // ← yml 설정값 사용
    }
    
    public String getRefreshToken(Map<String, Object> map){
        return genToken(map, refreshTokenExp); // ← yml 설정값 사용
    }

    //토큰 유효기간 확인
    public boolean verify(String token){
        boolean value = true;
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token); // 유효성 검사
        } catch (Exception e) {
            value = false;
        }
        return value;
    }

    //토근에 담긴 사용자정보(claims)를 반환
    public Map<String, Object> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)   // parseSignedClaims → parseClaimsJws
                .getBody();              // getPayload → getBody
    }
    
}