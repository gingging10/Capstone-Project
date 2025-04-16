package com.example.capstonetaekyoung.repository;
import com.example.capstonetaekyoung.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByCategory(String category);
}
