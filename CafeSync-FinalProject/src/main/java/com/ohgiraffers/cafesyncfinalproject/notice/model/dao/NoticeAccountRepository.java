package com.ohgiraffers.cafesyncfinalproject.notice.model.dao;

import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeAccountRepository extends JpaRepository<Account, String> {
    // 사용자 ID로 계정 조회
    Optional<Account> findById(String userId);
}
