package com.example.capstone.controller;

import com.example.capstone.domain.Party;
import com.example.capstone.dto.PartyCreateRequest;
import com.example.capstone.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody PartyCreateRequest request) {
        return ResponseEntity.ok(partyService.createParty(request));
    }

    @GetMapping
    public ResponseEntity<List<Party>> getAllParties(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(partyService.getAllParties(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> getPartyById(@PathVariable Long id) {
        return ResponseEntity.ok(partyService.getPartyById(id));
    }
}
