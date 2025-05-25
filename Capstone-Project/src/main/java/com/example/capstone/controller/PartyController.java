package com.example.capstone.controller;

import com.example.capstone.domain.Party;
import com.example.capstone.dto.PartyCreateRequest;
import com.example.capstone.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    // 🔹 HTML 렌더링 (템플릿 반환)
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

    // 🔹 API: JSON 응답
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
