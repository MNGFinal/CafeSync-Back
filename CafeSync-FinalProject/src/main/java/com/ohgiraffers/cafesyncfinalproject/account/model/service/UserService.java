package com.ohgiraffers.cafesyncfinalproject.account.model.service;

import com.ohgiraffers.cafesyncfinalproject.account.model.dao.UserRepository;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.UserDTO;
import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // ✅ 회원가입 (DTO → Entity 변환 후 저장)
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class); // DTO → Entity 변환
        user.setUserPass(passwordEncoder.encode(user.getUserPass())); // 비밀번호 암호화

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class); // 저장 후 DTO로 변환하여 반환
    }

    public User findUserById(String userId) {
        return userRepository.findByUserId(userId);
    }
}
