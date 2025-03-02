package com.ohgiraffers.cafesyncfinalproject.chat.model.entity;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.RoomType;
import com.ohgiraffers.cafesyncfinalproject.config.RoomTypeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "participants")
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ PK는 id에만 설정
    @Column(name = "id")
    private Long id;

    @Column(name = "room_id", nullable = false, unique = true)
    private Long roomId; // ✅ UUID or Timestamp로 직접 설정할 컬럼

    @Convert(converter = RoomTypeConverter.class) // ✅ 변환기 적용
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "room_name", nullable = false) // ✅ 추가
    private String roomName; // 채팅방 이름 추가

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomParticipant> participants;

    @Builder
    public ChatRoom(Long roomId, String roomName, RoomType roomType) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
    }
}


