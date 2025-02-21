package com.ohgiraffers.cafesyncfinalproject.note.model.dao;

import com.ohgiraffers.cafesyncfinalproject.note.model.entity.NoteUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteUpdateRepository extends JpaRepository<NoteUpdate, Integer> {
}
