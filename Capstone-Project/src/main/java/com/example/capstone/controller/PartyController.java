package com.example.capstone.controller;

import com.example.capstone.domain.Party;
import com.example.capstone.dto.PartyCreateRequest;
import com.example.capstone.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    // 메인 파티 리스트 페이지 렌더링
    @GetMapping({ "main", "/party/main-sports" })
    public String partyMainPage(Model model) {
        List<Party> parties = partyService.getAllParties("스포츠");
        model.addAttribute("parties", parties);
        return "party/main-sports";
    }

    @GetMapping({ "/party/main-delivery" })
    public String mainDeliveryPage(Model model) {
        List<Party> parties = partyService.getAllParties("배달");
        model.addAttribute("parties", parties);
        return "party/main-delivery";
    }

    @GetMapping({ "/party/main-games" })
    public String mainGamesPage(Model model) {
        List<Party> parties = partyService.getAllParties("게임");
        model.addAttribute("parties", parties);
        return "party/main-games";
    }

    @GetMapping({ "/party/main-groupbuy" })
    public String mainGroupBuyPage(Model model) {
        List<Party> parties = partyService.getAllParties("공구");
        model.addAttribute("parties", parties);
        return "party/main-groupbuy";
    }

    @GetMapping({ "/party/main-OTT" })
    public String mainOTTPage(Model model) {
        List<Party> parties = partyService.getAllParties("OTT");
        model.addAttribute("parties", parties);
        return "party/main-OTT";
    }

    // 파티 생성 폼
    @GetMapping("/parties/create-groupbuy")
    public String showBuyForm() {
        return "Create-Party/Create-Groupbuy";
    }

    @GetMapping("/parties/create-delivery")
    public String showDeliveryForm() {
        return "Create-Party/Create-Delivery";
    }

    @GetMapping("/parties/create-games")
    public String showGamesForm() {
        return "Create-Party/Create-Games";
    }

    @GetMapping("/parties/create-ott")
    public String showOTTForm() {
        return "Create-Party/Create-OTT";
    }

    @GetMapping("/parties/create-sports")
    public String showSportsForm() {
        return "Create-Party/Create-Sports";
    }

    @GetMapping("/party/apply-success")
    public String showApplySuccessPage() {
        return "party/party-apply-success";
    }

    @GetMapping("/party/creation-success")
    public String showCreationSuccessPage() {
        return "party/party-creation-success";
    }

    // 파티 상세페이지 매핑
    @GetMapping("/party/detail/{id}")
    public String partyDetailPage(@PathVariable Long id, Model model) {
        Party party = partyService.getPartyById(id);
        model.addAttribute("party", party);
        return "party/party-detail";
    }

    // JSON 응답
    @PostMapping("/api/parties")
    @ResponseBody
    public ResponseEntity<Party> createParty(@RequestBody PartyCreateRequest request) {
        return ResponseEntity.ok(partyService.createParty(request));
    }

    @GetMapping("/api/parties")
    @ResponseBody
    public ResponseEntity<List<Party>> getAllParties(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(partyService.getAllParties(category));
    }

    @GetMapping("/api/parties/{id}")
    @ResponseBody
    public ResponseEntity<Party> getPartyById(@PathVariable Long id) {
        return ResponseEntity.ok(partyService.getPartyById(id));
    }
}