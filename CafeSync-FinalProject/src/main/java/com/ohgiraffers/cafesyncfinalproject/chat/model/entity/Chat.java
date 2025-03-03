package com.ohgiraffers.cafesyncfinalproject.chat.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_chat")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "send_code", nullable = false)
    private Integer sendCode;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "send_time", nullable = false)
    private String sendTime;

    // ✅ 필드를 받는 생성자 추가!
    public Chat(Long roomId, Integer sendCode, String message, String sendTime) {
        this.roomId = roomId;
        this.sendCode = sendCode;
        this.message = message;
        this.sendTime = sendTime;
    }
}
