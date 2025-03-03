package com.ohgiraffers.cafesyncfinalproject.chat.model.service;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatReadRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRead;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ChatReadService {

    private final ChatReadRepository chatReadRepository;

    // 메세지 읽음처리
    @Transactional
    public void markMessageAsRead(Long chatId, Integer empCode) {
        ChatRead chatRead = chatReadRepository.findByChatIdAndEmpCode(chatId, empCode)
                .orElseThrow(() -> new RuntimeException("읽음 정보를 찾을 수 없습니다."));

        chatRead.setReadTime(LocalDateTime.now());
        chatReadRepository.save(chatRead);
    }
}
