package com.example.capstone.controller;

import com.example.capstone.domain.Member;
import com.example.capstone.dto.MemberResponseDto;
import com.example.capstone.dto.SignUpRequestDto;
import com.example.capstone.repository.MemberRepository;
import com.example.capstone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// 회원 관련 기능을 처리하는 컨트롤러
// 회원가입, 회원정보 조회, 회원정보 수정
// 경로 : /api/members

@RestController
@RequestMapping("/api/members") // 변경된 경로
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입 (POST /api/members)
    // 매개변수 dto: 회원가입 시 전달되는 정보(ID, 이름, 학번 학과, 전화번호, 이메일)
    // 리턴: 저장된 member 객체
    @PostMapping
    public ResponseEntity<Member> signUp(@RequestBody SignUpRequestDto dto) {
        Member saved = memberService.signUp(dto);
        return ResponseEntity.ok(saved);
    }

    // 회원 정보 조회 (GET /api/members/{id})
    // 매개변수 회원 ID (ID를 조회하여 나머지 정보 조회)
    // 리턴: MemberResponseDto (회원정보 응답용 DTO)
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable("id") String id) {
        return memberRepository.findById(id)
                .map(MemberResponseDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 회원 정보 수정 (PUT /api/members/{id})
    // 매개변수 id: 수정할 회원의 ID
    // 매개변수 dto: 수정할 회원정보 데이터
    // 리턴: 수정된 회원 정보를 담은 MemberResponseDto
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable("id") String id, @RequestBody SignUpRequestDto dto) {
        Member updated = memberService.updateMember(id, dto);
        return ResponseEntity.ok(new MemberResponseDto(updated));
    }
}