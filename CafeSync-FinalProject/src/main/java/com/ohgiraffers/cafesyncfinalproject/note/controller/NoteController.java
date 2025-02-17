package com.ohgiraffers.cafesyncfinalproject.note.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.service.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "바리스타 노트관련 스웨거 연동")
@RestController
@RequestMapping("/api/fran")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @Tag(name = "노트 전체조회", description = "노트 목록의 전체 조회")
    @GetMapping("/getAllNotes")
    public ResponseEntity<ResponseDTO> getAllNotes(){

        List<NoteDTO> getAllNotes = noteService.getAllNotes();
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회성공",getAllNotes));
    }

    @Tag(name = "노트 상세정보 조회", description = "노트의 상세정보 조회")
    @GetMapping("/getAllNotes/{noteCode}")
    public ResponseEntity<ResponseDTO> selectNoteByNoteCode(@PathVariable int noteCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상품 상세정보 조회 성공",  noteService.selectNoteByNoteCode(noteCode)));
    }
}
