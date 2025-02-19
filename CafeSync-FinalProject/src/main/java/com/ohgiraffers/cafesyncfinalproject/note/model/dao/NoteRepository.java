package com.ohgiraffers.cafesyncfinalproject.note.model.dao;

import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByNoteTitleContaining(String search);
}
