package com.ohgiraffers.cafesyncfinalproject.chat.controller;

import com.ohgiraffers.cafesyncfinalproject.chat.model.service.ChatPresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/presence")
@RequiredArgsConstructor
public class ChatPresenceController {

    private final ChatPresenceService chatPresenceService;

    // 접속 상태 업데이트 API
    // 예: /api/chat/presence/{roomId}/{empCode}?online=true 또는 false
    @PostMapping("/{roomId}/{empCode}")
    public ResponseEntity<String> updatePresence(
            @PathVariable Long roomId,
            @PathVariable Integer empCode,
            @RequestParam boolean online) {
        chatPresenceService.updatePresence(roomId, empCode, online);
        return ResponseEntity.ok("Presence updated");
    }
}
