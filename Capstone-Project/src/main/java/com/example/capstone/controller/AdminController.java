package com.example.capstone.controller;

import com.example.capstone.dto.AdminResponseDto;
import com.example.capstone.dto.MemberResponseDto;
import com.example.capstone.domain.Member;
import com.example.capstone.repository.MemberRepository;
import com.example.capstone.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")  // 경로 통일
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberRepository memberRepository;

    // 로그인 처리
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam("ad_id") String ad_id,
                                @RequestParam("ad_pw") String ad_pw,
                                HttpSession session) {

        AdminResponseDto dto = adminService.login(ad_id, ad_pw);

        if (dto != null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    dto.getAd_id(), null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            session.setAttribute("adminId", dto.getAd_id());

            System.out.println("[관리자 로그인  성공] ID: " + dto.getAd_id() + ", Email: " + dto.getAd_email());

            // ✅ JSON 형태로 redirect URL 응답
            return ResponseEntity.ok().body("{\"redirect\":\"/api/admin/Admin\"}");
        }

        System.out.println("[로그인 실패] ID: " + ad_id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "admin/Adminlogin";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/api/admin/login";
    }

    // 인증 실패 페이지
    @GetMapping("/unauthorized")
    public String unauthorizedPage() {
        return "admin/AdminUnauthorized";
    }

    // 관리자 메인 페이지
    @GetMapping("/Admin")
    public String adminMainPage(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) {
            return "redirect:/api/admin/unauthorized";
        }

        Map<String, Integer> percents = adminService.getMemberStatusPercents();
        model.addAttribute("percentStats", percents);  // HTML에서 이걸 씀
        return "admin/Admin";
    }


    // 사용자 리스트 페이지
    @GetMapping("/AdminUserlist")
    public String adminUserListPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                    Model model,
                                    HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return "redirect:/api/admin/unauthorized";
        }

        int pageSize = 10;
        Page<Member> memberPage = adminService.getPagedMembers(page, pageSize);

        List<MemberResponseDto> memberDtos = memberPage.getContent()
                .stream()
                .map(MemberResponseDto::new)
                .toList();

        model.addAttribute("members", memberDtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memberPage.getTotalPages());

        System.out.println("[회원 목록 조회] page=" + page + ", total=" + memberPage.getTotalElements());

        return "admin/AdminUserlist";
    }

    @PostMapping("/deactivate")
    public String deactivateMembers(@RequestParam("memberIds") List<String> memberIds,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {

        if (session.getAttribute("adminId") == null) {
            return "redirect:/api/admin/unauthorized";
        }

        if (memberIds == null || memberIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "선택된 회원이 없습니다.");
            return "redirect:/api/admin/AdminUserlist";
        }

        adminService.deactivateMembers(memberIds);
        redirectAttributes.addFlashAttribute("message", "선택된 회원의 권한이 제거되었습니다.");

        return "redirect:/api/admin/AdminUserlist";
    }

    @GetMapping("/AdminPartylist")
    public String adminPartyListPage(Model model, HttpSession session) {
        if (session.getAttribute("adminId") == null) {
            return "redirect:/api/admin/unauthorized";
        }

        List<Object[]> result = adminService.getAdminPartyList();

        model.addAttribute("partyList", result);
        System.out.println("[파티 목록 조회] " + result.size() + "개 조회됨");

        return "admin/AdminPartylist";
    }

    @PostMapping("/deleteParties")
    public String deleteParties(@RequestParam("selectedParties") List<Long> partyIds,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        if (session.getAttribute("adminId") == null) {
            return "redirect:/api/admin/unauthorized";
        }

        if (partyIds == null || partyIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "선택된 파티가 없습니다.");
            return "redirect:/api/admin/AdminPartylist";
        }

        adminService.deleteParties(partyIds);
        redirectAttributes.addFlashAttribute("message", "선택된 파티가 삭제되었습니다.");

        return "redirect:/api/admin/AdminPartylist";
    }



}
