package com.ohgiraffers.cafesyncfinalproject.chat.model.service;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatReadRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatMessage;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatReadRepository chatReadRepository;

    // ✅ 기존 채팅 내역 불러오기
    @Transactional(readOnly = true)
    public List<Chat> getChatHistory(Long roomId) {
        return chatRepository.findByRoomId(roomId);
    }

    // 안읽은 메세지 조회
    public int getUnreadMessageCount(Long roomId, Integer empCode) {

        return chatReadRepository.countUnreadMessages(roomId, empCode);
    }
}
