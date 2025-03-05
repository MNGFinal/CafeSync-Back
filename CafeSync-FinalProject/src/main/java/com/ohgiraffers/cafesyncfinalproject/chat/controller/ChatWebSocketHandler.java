package com.ohgiraffers.cafesyncfinalproject.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("새 WebSocket 연결: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        String payload = (String) message.getPayload();
        System.out.println("받은 메시지: " + payload);

        // JSON을 객체로 변환
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        // ✅ Redis에 메시지를 30초간 저장 (key: roomId)
        String key = "chat:" + chatMessage.getRoomId();
        redisTemplate.opsForList().rightPush(key, payload);
        redisTemplate.expire(key, 30, TimeUnit.SECONDS);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("WebSocket 오류 발생: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("WebSocket 연결 종료: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
