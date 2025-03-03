package com.ohgiraffers.cafesyncfinalproject.chat.model.dao;

import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.Chat;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoomId(Long roomId);

    // ✅ ChatRepository 인터페이스 수정
    boolean existsByRoomIdAndSendCodeAndMessage(Long roomId, Integer sendCode, String message);

}
