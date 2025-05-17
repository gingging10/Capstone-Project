package com.example.capstone.service;

import com.example.capstone.domain.Admin;
import com.example.capstone.domain.AdminStatus;
import com.example.capstone.domain.Member;
import com.example.capstone.domain.MemberStatus;
import com.example.capstone.dto.AdminResponseDto;
import com.example.capstone.repository.AdminRepository;
import com.example.capstone.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberRepository memberRepository;

    public AdminResponseDto login(String id, String pw) {
        Admin admin = adminRepository.findById(id).orElse(null);

        if (admin != null &&
            pw.equals(admin.getAd_pw()) &&  // 평문 비교
            admin.getAd_status() == AdminStatus.NORMAL) {

            System.out.println("[로그인 인증 성공] ID: " + id);
            return new AdminResponseDto(admin);
        }

        System.out.println("[로그인 인증 실패] ID: " + id);
        return null;
    }

    public Page<Member> getPagedMembers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        List<MemberStatus> visibleStatuses = List.of(MemberStatus.NORMAL, MemberStatus.VERIFIED); // 1, 2만 포함
        return memberRepository.findByStatusIn(visibleStatuses, pageable);
    }

    // 체크된 회원들의 권한 제거 (SUSPENDED로 변경)
    @Transactional
    public void deactivateMembers(List<String> memberIds) {
        List<Member> members = memberRepository.findAllById(memberIds);
        for (Member member : members) {
            member.setStatus(MemberStatus.SUSPENDED);  // 정지로 변경
        }
        memberRepository.saveAll(members); // 일괄 저장
    }
}
