package com.ohgiraffers.cafesyncfinalproject.chat.controller;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatMessage;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.Chat;
import com.ohgiraffers.cafesyncfinalproject.chat.model.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final ChatPresenceService chatPresenceService;
    private final ChatReadService chatReadService; // 읽음 처리 서비스

    @MessageMapping("/chat.sendMessage")
    public void handleMessage(@Payload ChatMessage chatMessage) {

        // 1. 채팅방의 참여자 목록 가져오기
        List<Integer> participants = chatRoomService.getParticipants(chatMessage.getRoomId());

        // 2. Redis에 메시지 저장 (기존 로직)
        chatMessageService.saveMessageToRedis(chatMessage, participants);

        // 3. DB에 즉시 메시지 저장 및 ChatRead 레코드 초기화, 저장된 Chat 객체 반환
        Chat savedChat = chatMessageService.saveMessageAndInitializeReadStatus(chatMessage);

        // 4. 접속 상태가 ON인 사용자에 대해 자동 읽음 처리
        for (Integer participantEmpCode : participants) {
            if (!participantEmpCode.equals(chatMessage.getSendCode())) {
                if (chatPresenceService.isUserOnline(chatMessage.getRoomId(), participantEmpCode)) {
                    chatReadService.markMessageAsRead(savedChat.getId(), participantEmpCode);
                }
            }
        }

        // 5. 모든 클라이언트에게 메시지 브로드캐스트
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
}
