package com.ohgiraffers.cafesyncfinalproject.note.model.dao;

import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
