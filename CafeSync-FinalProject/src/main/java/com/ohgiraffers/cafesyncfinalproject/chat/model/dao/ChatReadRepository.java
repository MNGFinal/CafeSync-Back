package com.ohgiraffers.cafesyncfinalproject.chat.model.dao;

import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRead;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatReadRepository extends JpaRepository<ChatRead, Long> {
    Optional<ChatRead> findByChatIdAndEmpCode(Long chatId, Integer empCode);

    @Query("""
    SELECT COUNT(cr)
    FROM ChatRead cr
    JOIN Chat ch ON ch.id = cr.chatId
    WHERE ch.roomId = :roomId
      AND cr.empCode = :empCode
      AND cr.readTime IS NULL
""")
    int countUnreadMessages(@Param("roomId") Long roomId,
                            @Param("empCode") Integer empCode);
}
