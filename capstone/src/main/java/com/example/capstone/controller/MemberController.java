package com.example.capstone.controller;

import com.example.capstone.domain.Member;
import com.example.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member saved = memberRepository.save(member);
        return ResponseEntity.ok(saved);
    }
}