package com.example.capstone.repository;

import com.example.capstone.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    // 기본적인 CRUD 메서드는 이미 포함되어 있음
    // 추가로 필요한 메서드는 여기에 정의 가능
}