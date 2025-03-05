package com.ohgiraffers.cafesyncfinalproject.chat.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatReadRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatRoomRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatMessage;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.Chat;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRead;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final StringRedisTemplate redisTemplate;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;
    private final ChatReadRepository chatReadRepository;

    // ✅ 30초마다 Redis에서 MySQL로 이동 (중복 저장 방지)
    @Scheduled(fixedRate = 30000)
    public void saveMessagesAndReadsFromRedisToMySQL() {
        System.out.println("🔄 Redis에서 메시지 & 읽음 정보를 MySQL로 이동 시작...");

        for (String key : redisTemplate.keys("chat:*")) {
            List<String> messages = redisTemplate.opsForList().range(key, 0, -1);

            if (messages != null && !messages.isEmpty()) {
                for (String jsonMessage : messages) {
                    try {
                        ChatMessage chatMessage = objectMapper.readValue(jsonMessage, ChatMessage.class);

                        // ✅ 중복 메시지 방지 (boolean 체크)
                        if (Boolean.TRUE.equals(chatRepository.existsByRoomIdAndSendCodeAndMessage(
                                chatMessage.getRoomId(),
                                chatMessage.getSendCode(),
                                chatMessage.getMessage()
                        ))) {
                            System.out.println("🚫 중복 메시지 저장 방지: " + chatMessage.getMessage());
                            continue;
                        }

                        // ✅ 메시지 저장 및 읽음 정보 초기화
                        saveMessageAndInitializeReadStatus(chatMessage);

                    } catch (Exception e) {
                        System.err.println("❌ MySQL 저장 중 오류: " + e.getMessage());
                    }
                }
                redisTemplate.delete(key); // ✅ 중복 저장 방지
            }
        }
    }


    public void saveMessageToRedis(ChatMessage chatMessage, List<Integer> participants) {
        try {
            String key = "chat:" + chatMessage.getRoomId();

            // ✅ 한글 깨짐 방지 (UTF-8 인코딩 적용)
            String chatJson = objectMapper.writeValueAsString(chatMessage);
            byte[] utf8EncodedJson = chatJson.getBytes(StandardCharsets.UTF_8);
            String encodedMessage = new String(utf8EncodedJson, StandardCharsets.UTF_8);

            redisTemplate.opsForList().rightPush(key, encodedMessage);
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
            System.out.println("📌 Redis에 저장된 메시지 (UTF-8 인코딩 적용): " + encodedMessage);

        } catch (Exception e) {
            System.err.println("❌ Redis 저장 중 오류 발생: " + e.getMessage());
        }
    }



    @Transactional
    public Chat saveMessageAndInitializeReadStatus(ChatMessage chatMessage) {
        // 메시지 저장
        Chat savedChat = chatRepository.save(new Chat(
                chatMessage.getRoomId(),
                chatMessage.getSendCode(),
                chatMessage.getMessage(),
                LocalDateTime.now().toString()
        ));
        System.out.println("✅ 저장된 채팅 메시지 ID: " + savedChat.getId());

        // 채팅방 참여자 목록 조회
        List<Integer> participants = chatRoomRepository.findParticipantsByRoomId(chatMessage.getRoomId());
        System.out.println("🔍 참여자 목록: " + participants);

        // 참여자별로 ChatRead 레코드 생성
        for (Integer empCode : participants) {
            ChatRead chatRead = new ChatRead(
                    savedChat.getId(),
                    empCode,
                    empCode.equals(chatMessage.getSendCode()) ? LocalDateTime.now() : null
            );
            try {
                chatReadRepository.save(chatRead);
                System.out.println("✅ 저장 완료: " + chatRead);
            } catch (Exception e) {
                System.err.println("❌ chatRead 저장 실패: " + e.getMessage());
            }
        }

        // 여기서 savedChat을 반환해야, 호출부에서 Chat 객체를 받을 수 있음
        return savedChat;
    }


    public List<ChatMessage> getMessagesFromRedis(Long roomId) {
        String key = "chat:" + roomId;
        List<String> jsonMessages = redisTemplate.opsForList().range(key, 0, -1);

        if (jsonMessages == null || jsonMessages.isEmpty()) {
            System.out.println("🚨 Redis에서 채팅 메시지가 없음: " + roomId);
            return List.of(); // 빈 리스트 반환
        }

        try {
            List<ChatMessage> chatMessages = jsonMessages.stream()
                    .map(json -> {
                        try {
                            // ✅ UTF-8 디코딩 적용
                            byte[] decodedBytes = json.getBytes(StandardCharsets.UTF_8);
                            String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

                            ChatMessage message = objectMapper.readValue(decodedJson, ChatMessage.class);
                            System.out.println("📩 변환된 메시지 (UTF-8 디코딩 적용): " + message);
                            return message;
                        } catch (Exception e) {
                            System.err.println("❌ JSON 변환 오류: " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(msg -> msg != null)
                    .toList();

            System.out.println("📌 Redis에서 최종 반환할 메시지 목록: " + chatMessages);
            return chatMessages;
        } catch (Exception e) {
            System.err.println("❌ Redis 메시지 조회 중 오류 발생: " + e.getMessage());
            return List.of(); // 오류 발생 시 빈 리스트 반환
        }
    }
}
