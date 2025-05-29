package com.example.capstonetaekyoung.controller;

import com.example.capstonetaekyoung.domain.Party;
import com.example.capstonetaekyoung.domain.Member;
import com.example.capstonetaekyoung.dto.PartyCreateRequest;
import com.example.capstonetaekyoung.dto.PartyUpdateRequest;
import com.example.capstonetaekyoung.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    @GetMapping
    public List<Party> filterParties(@RequestParam(required = false) String category,
                                     @RequestParam(required = false) String keyword) {
        return partyService.filterParties(category, keyword);
    }

    @PostMapping("/{partyId}/join")
    public ResponseEntity<?> joinParty(@PathVariable Long partyId, @RequestParam Long memberId) {
        try {
            partyService.joinParty(partyId, memberId);
            return ResponseEntity.ok("파티 참여 완료");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{partyId}/leave")
    public ResponseEntity<?> leaveParty(@PathVariable Long partyId, @RequestParam Long memberId) {
        try {
            partyService.leaveParty(partyId, memberId);
            return ResponseEntity.ok("파티 참여 취소 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{partyId}/close")
    public ResponseEntity<?> closeParty(@PathVariable Long partyId) {
        partyService.closeParty(partyId);
        return ResponseEntity.ok("모집이 마감되었습니다.");
    }

    @GetMapping("/{partyId}/participants")
    public ResponseEntity<List<Member>> getParticipants(@PathVariable Long partyId) {
        List<Member> members = partyService.getParticipants(partyId);
        return ResponseEntity.ok(members);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createParty(@RequestBody PartyCreateRequest request) {
        Long partyId = partyService.createParty(request).getId();
        return ResponseEntity.ok("파티 생성 완료, ID: " + partyId);
    }

    protected Party updateParty(Long id, PartyUpdateRequest request) {
    }
}
    @PutMapping("/{id}")
    public ResponseEntity<Party> updateParty(@PathVariable Long id,
                                         @RequestBody PartyUpdateRequest request) {
     PartyController partyService = null;
        return ResponseEntity.ok(partyService.updateParty(id, request));
}
}