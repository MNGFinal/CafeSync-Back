package com.ohgiraffers.cafesyncfinalproject.notice.model.dao;

import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    List<Notice> findByNoticeTitleContaining(String search);
}
