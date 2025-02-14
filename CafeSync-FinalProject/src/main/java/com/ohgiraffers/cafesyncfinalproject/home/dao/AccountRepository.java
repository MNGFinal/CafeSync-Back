package com.ohgiraffers.cafesyncfinalproject.home.dao;

import com.ohgiraffers.cafesyncfinalproject.home.entity.Account;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    // 가맹점 코드, 사원 코드, 이메일 일치하는지 검증
    Account findByStoreCodeAndEmpCodeAndEmail(int storeCode, int empCode, String email);

    // 아이디와 이메일이 일치하는지 검증
    boolean existsByUserIdAndEmail(String userId, String email);

    // 새 비밀번호 변경
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_account SET user_pass = :encodedPassword WHERE user_id = :userId", nativeQuery = true)
    int updatePassword(@Param("userId") String userId, @Param("encodedPassword") String encodedPassword);


    // 아이디 값으로 직전 사용한 비밀번호 조회
    Optional<Account> findByUserId(String userId);
}
