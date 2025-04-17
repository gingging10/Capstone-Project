package com.example.capstone.controller;

import com.example.capstone.domain.Member;
import com.example.capstone.dto.MemberResponseDto;
import com.example.capstone.dto.SignUpRequestDto;
import com.example.capstone.repository.MemberRepository;
import com.example.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members") // ✅ 변경된 경로
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입 (POST /api/members)
    @PostMapping
    public ResponseEntity<Member> signUp(@RequestBody SignUpRequestDto dto) {
        Member saved = memberService.signUp(dto);
        return ResponseEntity.ok(saved);
    }

    // 회원 정보 조회 (GET /api/members/{id})
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable("id") String id) {
        return memberRepository.findById(id)
                .map(MemberResponseDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}