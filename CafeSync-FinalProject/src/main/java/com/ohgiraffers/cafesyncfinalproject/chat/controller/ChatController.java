package com.ohgiraffers.cafesyncfinalproject.chat.controller;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatMessage;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatRoomDTO;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatRoomRequest;

import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.Chat;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRoom;
import com.ohgiraffers.cafesyncfinalproject.chat.model.service.ChatMessageService;
import com.ohgiraffers.cafesyncfinalproject.chat.model.service.ChatReadService;
import com.ohgiraffers.cafesyncfinalproject.chat.model.service.ChatRoomService;
import com.ohgiraffers.cafesyncfinalproject.chat.model.service.ChatService;
import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatReadService chatReadService;
    private final ChatService chatService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;


    // ✅ 채팅방 생성 API
    @Operation(
            summary = "채팅방 생성",
            description = "새로운 채팅방을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 생성 성공!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "서버 오류!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
            }
    )
    @PostMapping("/room")
    public ResponseEntity<ResponseDTO> createChatRoom(@RequestBody ChatRoomRequest request) {

        try {
            if (request.getRoomName() == null || request.getRoomName().trim().isEmpty() ||
                    request.getMemberEmpCodes() == null || request.getMemberEmpCodes().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "잘못된 요청 데이터!", null));
            }

            // ✅ 채팅방 생성 서비스 호출
            ChatRoom chatRoom = chatRoomService.createChatRoom(
                    request.getRoomName(),
                    request.getMemberEmpCodes()
            );

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "채팅방 생성 성공!", chatRoom));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생!", null));
        }
    }

    @Operation(
            summary = "로그인한 사용자의 채팅방 목록 조회",
            description = "로그인한 사용자가 참여 중인 모든 채팅방을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 목록 조회 성공!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "서버 오류 발생!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)))
            }
    )
    @GetMapping("/rooms/{empCode}")
    public ResponseEntity<ResponseDTO> getChatRoomsByEmpCode(@PathVariable Integer empCode) {

        try {
            List<ChatRoomDTO> chatRooms = chatRoomService.getChatRoomsByEmpCode(empCode);


            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "채팅방 목록 조회 성공!", chatRooms));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생!", null));
        }
    }

    @GetMapping("/history/{roomId}")
    public ResponseEntity<ResponseDTO> getChatHistory(@PathVariable Long roomId) {
        System.out.println("roomId = " + roomId);

        try {
            // ✅ 1️⃣ MySQL에서 저장된 채팅 내역 가져오기 (과거 대화)
            List<Chat> mysqlMessages = chatService.getChatHistory(roomId);
            System.out.println("💾 MySQL에서 가져온 채팅 내역: " + mysqlMessages);

            // ✅ 2️⃣ Redis에서 최근 채팅 내역 가져오기 (즉시 전송된 메시지)
            List<ChatMessage> redisMessages = chatMessageService.getMessagesFromRedis(roomId);
            System.out.println("🚀 Redis에서 가져온 채팅 내역: " + redisMessages);

            // ✅ 3️⃣ MySQL 메시지 + Redis 메시지를 합쳐서 정렬 후 반환
            List<Object> allMessages = new ArrayList<>();
            allMessages.addAll(mysqlMessages);  // ✅ 과거 대화 먼저 추가
            allMessages.addAll(redisMessages);  // ✅ 최신 대화 추가

            // ✅ 4️⃣ 시간순 정렬 (sendTime 기준)
            allMessages.sort((a, b) -> {
                LocalDateTime timeA = parseSendTime(a);
                LocalDateTime timeB = parseSendTime(b);
                return timeA.compareTo(timeB);
            });

            System.out.println("📌 최종 반환할 채팅 내역: " + allMessages);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "채팅 내역 조회 성공!", allMessages));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생!", null));
        }
    }

    /**
     * ✅ sendTime을 LocalDateTime으로 변환하는 메서드
     */
    private LocalDateTime parseSendTime(Object messageObj) {
        try {
            String sendTime = "";
            if (messageObj instanceof ChatMessage) {
                sendTime = ((ChatMessage) messageObj).getSendTime();
            } else if (messageObj instanceof Chat) {
                sendTime = ((Chat) messageObj).getSendTime();
            }

            // ✅ 다양한 날짜 형식을 처리할 포맷 리스트
            List<DateTimeFormatter> formatters = List.of(
                    DateTimeFormatter.ofPattern("yy.MM.dd HH:mm:ss"), // 25.02.27 16:06:07
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // 2025-02-27 17:19:28
                    DateTimeFormatter.ISO_DATE_TIME // 2025-02-27T17:19:28
            );

            for (DateTimeFormatter formatter : formatters) {
                try {
                    return LocalDateTime.parse(sendTime, formatter);
                } catch (Exception ignored) {
                    // 하나라도 성공하면 return 되므로, 실패한 것은 무시
                }
            }

            throw new DateTimeParseException("모든 포맷 시도 실패", sendTime, 0);
        } catch (Exception e) {
            System.err.println("❌ 시간 변환 오류: " + e.getMessage());
            return LocalDateTime.now(); // 변환 실패 시 현재 시간 반환
        }
    }

    // 메세지 읽음 처리
    @PostMapping("/read/{chatId}/{empCode}")
    public ResponseEntity<String> markMessageAsRead(
            @PathVariable Long chatId,
            @PathVariable Integer empCode) {

        chatReadService.markMessageAsRead(chatId, empCode);
        return ResponseEntity.ok("메시지 읽음 처리 완료");
    }


    // ✅ 채팅방 별 안 읽은 메시지 개수 조회 API
    @GetMapping("/unread/{roomId}/{empCode}")
    public ResponseEntity<Integer> getUnreadCount(@PathVariable Long roomId,
                                                  @PathVariable Integer empCode) {
        int unreadCount = chatService.getUnreadMessageCount(roomId, empCode);

        System.out.println("몇개를 안읽었니 = " + unreadCount);

        return ResponseEntity.ok(unreadCount);
    }

    @DeleteMapping("/room/{roomId}/participants/{empCode}")
    public ResponseEntity<?> leaveRoom(
            @PathVariable Long roomId,
            @PathVariable Integer empCode
    ) {
        // 2) DB에서 empCode -> empName 조회 (제거하기 전에 미리 가져오기)
        String empName = chatRoomService.findEmpNameByEmpCode(empCode);

        // 1) 채팅방에서 참여자 제거
        chatRoomService.removeParticipant(roomId, empCode);

        System.out.println("채팅방 나간 사람 이름 = " + empName);

        // 3) 시스템 메시지 생성 (LEAVE 타입)
        ChatMessage systemMessage = new ChatMessage();
        systemMessage.setRoomId(roomId);
        systemMessage.setSendCode(empCode);
        systemMessage.setMessage(empName + "님이 채팅방에서 나가셨습니다.");
        systemMessage.setType("LEAVE");
        systemMessage.setSendTime(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm:ss")));

        // 4) WebSocket 브로드캐스트
        messagingTemplate.convertAndSend("/topic/room/" + roomId, systemMessage);

        return ResponseEntity.ok("채팅방 나가기 완료");
    }
}
