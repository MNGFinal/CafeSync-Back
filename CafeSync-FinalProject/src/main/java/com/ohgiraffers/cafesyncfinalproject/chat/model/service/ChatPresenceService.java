package com.ohgiraffers.cafesyncfinalproject.chat.model.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatPresenceService {

    // Key: roomId, Value: Map<empCode, online 상태>
    private final Map<Long, Map<Integer, Boolean>> presenceMap = new ConcurrentHashMap<>();

    // 접속 상태 업데이트: 해당 방(roomId)의 사용자가 online이면 true, 아니면 false
    public void updatePresence(Long roomId, Integer empCode, boolean online) {
        presenceMap.computeIfAbsent(roomId, key -> new ConcurrentHashMap<>())
                .put(empCode, online);
        System.out.println("Room " + roomId + " - empCode " + empCode + " online: " + online);
    }

    // 해당 채팅방에서 empCode 사용자가 접속 중인지 확인
    public boolean isUserOnline(Long roomId, Integer empCode) {

        boolean isOnline = presenceMap.getOrDefault(roomId, Map.of()).getOrDefault(empCode, false);
        System.out.println("🔍 [PresenceCheck] roomId=" + roomId + ", empCode=" + empCode + ", isOnline=" + isOnline);
        return isOnline;
    }
}
