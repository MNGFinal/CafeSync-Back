package com.ohgiraffers.cafesyncfinalproject.home.dao;

import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import com.ohgiraffers.cafesyncfinalproject.home.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByStoreCodeAndEmpCodeAndEmail(int storeCode, int empCode, String email);
}
