package com.ohgiraffers.cafesyncfinalproject.note.model.dao;

import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    // 기본적인 CRUD가 자동으로 제공되므로, 필요하다면 추가적인 메서드를 작성할 수 있습니다.
}
