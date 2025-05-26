package com.example.capstone.service;

import com.example.capstone.domain.Member;
import com.example.capstone.domain.MemberStatus;
import com.example.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    // 이메일별 인증번호 저장소 (이메일 → 인증번호, 생성시간 포함)
    private final Map<String, CodeInfo> codeStorage = new HashMap<>();

    /**
     * 인증번호 전송
     */
    public void sendCode(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 사용자가 없습니다."));

        if (member.getStatus() == MemberStatus.VERIFIED) {
            throw new IllegalStateException("이미 인증된 회원입니다.");
        }

        String code = emailService.sendVerificationCode(email);
        codeStorage.put(email, new CodeInfo(code, System.currentTimeMillis()));
    }

    /**
     * 인증번호 검증
     */
    public boolean verifyCode(String email, String code) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 사용자가 없습니다."));

        if (member.getStatus() == MemberStatus.VERIFIED) {
            throw new IllegalStateException("이미 인증된 회원입니다.");
        }

        CodeInfo codeInfo = codeStorage.get(email);
        if (codeInfo == null || isExpired(codeInfo)) {
            codeStorage.remove(email);
            throw new IllegalArgumentException("인증번호가 존재하지 않거나 만료되었습니다.");
        }

        if (!code.equals(codeInfo.getCode())) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        member.setStatus(MemberStatus.VERIFIED);
        memberRepository.save(member);
        codeStorage.remove(email);

        return true;
    }

    private boolean isExpired(CodeInfo info) {
        long now = System.currentTimeMillis();
        return (now - info.getTimestamp()) > 1 * 60 * 1000; // 3분 유효
    }
}
