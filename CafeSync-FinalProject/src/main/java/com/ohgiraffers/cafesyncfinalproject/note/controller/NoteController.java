package com.ohgiraffers.cafesyncfinalproject.note.controller;

import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Note", description = "바리스타 노트 관련 API")
@RequestMapping("/api/fran")
@RestController
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "전체 노트 조회", description = "저장된 모든 노트를 조회합니다.")
    @GetMapping("/notes")
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> noteList = noteService.getAllNotes();
        return ResponseEntity.ok(noteList);
    }
}
