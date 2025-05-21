package com.example.capstone.controller;

import com.example.capstone.dto.MyPageResponse;
import com.example.capstone.dto.PartyListDto;
import com.example.capstone.service.MyPageService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public String myPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String id;
        if (principal == null) {
            // 테스트용 기본 id (DB에 존재하는 테스트 계정 id로 변경)
            id = "userA";
        } else {
            id = principal.getAttribute("id");
        }
        MyPageResponse myPage = myPageService.getMyPage(id);
        model.addAttribute("myPage", myPage);
        return "mypage/mypage";
    }

    @GetMapping("/edit")
    public String editMyPage(Model model, @RequestParam String id) {
        MyPageResponse myPage = myPageService.getMyPage(id);
        model.addAttribute("myPage", myPage);
        return "mypage/mypage-edit";
    }

    @PostMapping("/edit")
    public String updateMyPage(@ModelAttribute MyPageResponse myPage) {
        myPageService.updateMyPage(myPage);
        return "redirect:/mypage?id=" + myPage.getId();
    }

    @GetMapping("/my-parties")
    public String myParties(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @AuthenticationPrincipal OAuth2User principal) {
        // 실제 DB에 존재하는 id로 임시 변경
        String id = (principal != null) ? principal.getAttribute("id") : "userA";
        int pageSize = 10;
        List<PartyListDto> partyList = myPageService.getMyParties(id, page, pageSize);
        int totalCount = myPageService.getMyPartiesCount(id);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("partyList", partyList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "mypage/my-parties";
    }
}