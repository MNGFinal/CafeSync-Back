package com.ohgiraffers.cafesyncfinalproject.chat.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoomRequest {

    private Long roomId;
    private String roomName;
    private List<Integer> memberEmpCodes; // 참여자 사번 리스트
}
