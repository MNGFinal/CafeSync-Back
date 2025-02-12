package com.ohgiraffers.cafesyncfinalproject.account.model.service;

import com.ohgiraffers.cafesyncfinalproject.account.model.dao.UserRepository;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.UserDetailsImpl;
import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

// 사용자 정보를 가져오는 서비스
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserId(username);

        return new UserDetailsImpl(user);
    }
}