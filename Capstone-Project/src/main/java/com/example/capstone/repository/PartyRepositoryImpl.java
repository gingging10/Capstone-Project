package com.example.capstone.repository;

import com.example.capstone.domain.Party;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartyRepositoryImpl implements PartyRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

@Override
public List<Object[]> fetchAdminPartyList() {
    return em.createQuery(
        "SELECT p.id, p.title, " +  // ✅ p.id가 0번, p.title이 1번
        "(SELECT r.reason FROM Report r WHERE r.reporterId = p.creator.id AND r.targetId = CAST(p.id AS string)), " +
        "p.category, p.deadline, " +
        "(SELECT COUNT(j) FROM Join j WHERE j.partyId = p.id), " +
        "p.chatLink " +
        "FROM Party p",
        Object[].class
    ).getResultList();
}


}
