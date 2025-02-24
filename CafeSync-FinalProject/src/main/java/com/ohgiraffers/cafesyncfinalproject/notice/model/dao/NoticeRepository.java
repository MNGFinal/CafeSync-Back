package com.ohgiraffers.cafesyncfinalproject.notice.model.dao;

import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
