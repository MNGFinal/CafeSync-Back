package com.ohgiraffers.cafesyncfinalproject.chat.model.dao;

import aj.org.objectweb.asm.commons.Remapper;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT DISTINCT cr FROM ChatRoom cr " +
            "JOIN cr.participants cp " +
            "JOIN cp.employee e " + // 🔥 직원과도 조인
            "WHERE e.empCode = :empCode")
    List<ChatRoom> findChatRoomsByEmpCode(@Param("empCode") Integer empCode);

    Optional<ChatRoom> findByRoomId(Long roomId);

    @Query("SELECT p.employee.empCode FROM ChatRoomParticipant p WHERE p.chatRoom.roomId = :roomId")
    List<Integer> findParticipantsByRoomId(@Param("roomId") Long roomId);

}
