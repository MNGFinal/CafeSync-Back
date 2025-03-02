package com.ohgiraffers.cafesyncfinalproject.chat.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class ChatMessage {
    private Long roomId;
    private Integer sendCode;
    private String message;
    private String type;
    private String sendTime;
}
