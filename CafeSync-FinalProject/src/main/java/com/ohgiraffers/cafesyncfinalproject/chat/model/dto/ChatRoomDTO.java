package com.ohgiraffers.cafesyncfinalproject.chat.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoomDTO {
    private Long roomId;
    private String roomName;
    private String roomType;
    private List<ChatRoomParticipantDTO> participants;
}
