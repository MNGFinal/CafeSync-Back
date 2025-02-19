package com.ohgiraffers.cafesyncfinalproject.note.model.dao;

import com.ohgiraffers.cafesyncfinalproject.note.model.entity.NoteInsert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteInsertRepository extends JpaRepository<NoteInsert, Integer> {
}
