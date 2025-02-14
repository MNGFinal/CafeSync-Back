package com.ohgiraffers.cafesyncfinalproject.note.model.service;

import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    // Note 전체 조회
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .map(note -> new NoteDTO(
                        note.getNoteCode(),
                        note.getNoteTitle(),
                        note.getNoteDate(),
                        note.getNoteDetail(),
                        note.getAttachment(),
                        note.getUserId()))
                .collect(Collectors.toList());
    }
}
