package com.ohgiraffers.cafesyncfinalproject.account.model.service;

import com.ohgiraffers.cafesyncfinalproject.account.model.dao.UserRepository;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.UserDetailsImpl;
import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserId(username));

        // ✅ 사용자 없을 경우 예외 처리
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username)
        );

        return new UserDetailsImpl(user);
    }
}
