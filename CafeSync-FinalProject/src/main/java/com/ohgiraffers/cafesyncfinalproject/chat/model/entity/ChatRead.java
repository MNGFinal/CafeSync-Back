package com.ohgiraffers.cafesyncfinalproject.chat.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_chat_read")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId; // 메시지 ID

    @Column(name = "emp_code", nullable = false)
    private Integer empCode; // 읽은 사람 사번 코드

    @Column(name = "read_time")
    private LocalDateTime readTime;

    // ✅ 필드를 받는 생성자 추가
    public ChatRead(Long chatId, Integer empCode, LocalDateTime readTime) {
        this.chatId = chatId;
        this.empCode = empCode;
        this.readTime = readTime;
    }
}
