package com.ohgiraffers.cafesyncfinalproject.note.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import com.ohgiraffers.cafesyncfinalproject.note.model.service.NoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"노트 전체 조회성공",getAllNotes));
    }

    @Tag(name = "노트 상세정보 조회", description = "노트의 상세정보 조회")
    @GetMapping("/getAllNotes/{noteCode}")
    public ResponseEntity<ResponseDTO> selectNoteByNoteCode(@PathVariable int noteCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "노트 상세정보 조회 성공",  noteService.selectNoteByNoteCode(noteCode)));
    }

    @Tag(name="노트 검색", description = "검색 조건에 맞는 노트를 조회")
    @GetMapping("/getAllNotes/search")
    public ResponseEntity<ResponseDTO> selectSearchProductList(
            @RequestParam(name = "search", defaultValue = "all") String search){

        return ResponseEntity
                .ok().body(new ResponseDTO(HttpStatus.OK, "노트 검색 성공", noteService.selectNoteBySearch(search)));
    }

    @Tag(name = "바리스타 노트 등록", description = "바리스타 노트 정보를 등록")
    @PostMapping("/notes")
    public ResponseEntity<ResponseDTO> insertNote(@RequestBody NoteInsertDTO noteDTO, Principal principal) {
        System.out.println("✅ Received NoteDTO: " + noteDTO);
        try {
            int noteCode = noteService.insertNote(noteDTO, principal);
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "노트 등록 성공", noteCode));
        } catch (Exception e) {
            e.printStackTrace(); // ✅ 전체 스택 트레이스 출력 (콘솔에서 확인 가능)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "노트 등록 실패", e.getMessage()));
        }
    }
}
