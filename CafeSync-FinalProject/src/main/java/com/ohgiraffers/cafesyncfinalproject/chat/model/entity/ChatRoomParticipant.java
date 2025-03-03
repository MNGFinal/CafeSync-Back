package com.ohgiraffers.cafesyncfinalproject.chat.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_chat_room_participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@ToString(exclude = "chatRoom")
@Builder
public class ChatRoomParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false) // ✅ FK 매핑
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "emp_code", referencedColumnName = "emp_code")
    private Employee employee; // 직원 엔티티

    public ChatRoomParticipant(ChatRoom chatRoom, Employee employee) { // ✅ 생성자 추가
        this.chatRoom = chatRoom;
        this.employee = employee;
    }
}

