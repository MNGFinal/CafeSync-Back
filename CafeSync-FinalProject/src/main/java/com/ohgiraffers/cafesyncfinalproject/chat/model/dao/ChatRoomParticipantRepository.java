package com.ohgiraffers.cafesyncfinalproject.chat.model.dao;

import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRoomParticipant;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long> {
    @Modifying
    @Query(value = "DELETE FROM tbl_chat_room_participant WHERE room_id = :roomId AND emp_code = :empCode", nativeQuery = true)
    void removeNative(@Param("roomId") Long roomId, @Param("empCode") Integer empCode);

    @Query(value = """
    SELECT e.emp_name
    FROM tbl_employee e
    LEFT JOIN tbl_chat_room_participant p ON e.emp_code = p.emp_code
    WHERE e.emp_code = :empCode
    LIMIT 1
    """, nativeQuery = true)
    Optional<String> findEmpNameByEmpCodeNative(@Param("empCode") Integer empCode);
}