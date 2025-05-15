package com.example.capstone.service;

import com.example.capstone.domain.Admin;
import com.example.capstone.domain.AdminStatus;
import com.example.capstone.domain.Member;
import com.example.capstone.domain.MemberStatus;
import com.example.capstone.domain.Join;

import com.example.capstone.dto.AdminResponseDto;
import com.example.capstone.repository.AdminRepository;
import com.example.capstone.repository.MemberRepository;
import com.example.capstone.repository.PartyRepository;
import com.example.capstone.repository.JoinRepository;
import com.example.capstone.repository.PartyRepositoryCustom;
import com.example.capstone.repository.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyRepositoryCustom partyRepositoryCustom;

    @Autowired
    private JoinRepository joiningRepository;

    @Autowired
    private ReportRepository reportRepository;

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


    public Map<String, Integer> getMemberStatusPercents() {
        long totalCount = memberRepository.countAllMembers();
        long denominator = Math.max(totalCount, 1); // 0 방지

        Map<String, Integer> percents = new HashMap<>();
        System.out.println("total=" + totalCount);
        System.out.println("활성화active=" + memberRepository.countActiveMembers());
        System.out.println("기본normal=" + memberRepository.countNormalMembers());
        System.out.println("verified=" + memberRepository.countVerifiedMembers());
        percents.put("total", 100); // 전체는 항상 100%
        percents.put("active", (int)(memberRepository.countActiveMembers() * 100 / denominator));
        percents.put("normal", (int)(memberRepository.countNormalMembers() * 100 / denominator));
        percents.put("verified", (int)(memberRepository.countVerifiedMembers() * 100 / denominator));
        return percents;
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

    public List<Object[]> getAdminPartyList() {
        return partyRepositoryCustom.fetchAdminPartyList(); // ✅ 안전하게 커스텀 메서드 호출
    }

    @Transactional
    public void deleteParties(List<Long> partyIds) {
        // 1. joining 먼저 삭제 (직접 JPQL로)
        System.out.println("[삭제] joining 삭제 시작: " + partyIds);
        joiningRepository.deleteByPartyIds(partyIds); // ← 이 줄만 있으면 됨

        // 2. report 삭제
        List<String> partyIdStrings = partyIds.stream()
            .map(String::valueOf)
            .toList();
        System.out.println("[삭제] report 삭제 시작: " + partyIdStrings);
        reportRepository.deleteByTargetIdIn(partyIdStrings);

        // 3. party 삭제
        System.out.println("[삭제] party 삭제 시작: " + partyIds);
        partyRepository.deleteAllByIdInBatch(partyIds);
    }



}
