package com.example.capstone.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("customerServiceTime", "오전 9시 - 오후 6시");
        model.addAttribute("customerServicePhone", "1234-5678");
        model.addAttribute("customerServiceKakao", "카카오 문의");

        // ✅ 중복 제거
        model.addAttribute("companyInfo", List.of("회사소개", "인재채용", "광고제휴문의"));
        model.addAttribute("services", List.of("스포츠", "배달", "공구", "게임", "OTT"));
        model.addAttribute("faqs", List.of("자주묻는질문", "공지사항"));

        model.addAttribute("footerInfo1", "(주)test | 주소 : 강원특별자치도 춘천시 효자동 192-1 | 대표이사 : 김철수 | 사업자등록번호 : 123-45-67890");
        model.addAttribute("footerInfo2", "전자우편주소 : test@naver.com | 전화번호 : 1234-5678 | 호스팅서비스제공자 : (주)test");
        model.addAttribute("footerInfo3", "(주)test는 통신판매중개자로서 통신판매의 당사자가 아니며, 관련 책임은 판매자에게 있습니다.");

        model.addAttribute("termsLink", "/terms");
        model.addAttribute("privacyPolicyLink", "/privacy");
        model.addAttribute("consumerPolicyLink", "/consumer-policy");
        model.addAttribute("contentPolicyLink", "/content-law");

        model.addAttribute("copyright", "Copyright GC COMPANY Corp. All rights reserved.");
    }
}
