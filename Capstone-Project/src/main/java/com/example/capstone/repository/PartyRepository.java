package com.example.capstone.repository;
import com.example.capstone.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByCategory(String category);
    void deleteAllByIdInBatch(Iterable<Long> ids);
}
