package com.ohgiraffers.cafesyncfinalproject.chat.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoomParticipantDTO {
    private Integer empCode;
    private String empName;
    private String profileImage;
}
