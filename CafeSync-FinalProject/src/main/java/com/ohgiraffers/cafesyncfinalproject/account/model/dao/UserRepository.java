package com.ohgiraffers.cafesyncfinalproject.account.model.dao;

import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String username);
}
