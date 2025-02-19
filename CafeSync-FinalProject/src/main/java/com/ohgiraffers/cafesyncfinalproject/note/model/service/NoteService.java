package com.ohgiraffers.cafesyncfinalproject.note.model.service;

import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteInsertRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.NoteInsert;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;
    private final NoteInsertRepository noteInsertRepository;

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
                        (note.getAccount() != null) ? note.getAccount().getUserId() : null, // account에서 userId 가져오기
                        (note.getAccount() != null && note.getAccount().getEmployee() != null) ?
                                note.getAccount().getEmployee().getEmpName() : null))  // empName 가져오기
                .collect(Collectors.toList());
    }


    public NoteDTO selectNoteByNoteCode(int noteCode) {
        Note note = noteRepository.findById(noteCode).get();
        NoteDTO noteDTO = modelMapper.map(note,NoteDTO.class);
        return noteDTO;
    }

    public List<NoteDTO> selectNoteBySearch(String search) {
        List<Note> noteListWithSearchValue = noteRepository.findByNoteTitleContaining(search);

        List<NoteDTO> noteDTOList = noteListWithSearchValue.stream()
                .map(note -> {
                    NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
                    noteDTO.setUserId(note.getAccount().getUserId());  // Account의 userId 설정
                    noteDTO.setEmpName(note.getAccount().getEmployee().getEmpName());  // Account의 empName 설정
                    return noteDTO;
                })
                .collect(Collectors.toList());

        return noteDTOList;
    }

    @Transactional
    public int insertNote(NoteInsertDTO noteDTO, Principal principal) {
        if (noteDTO == null) {
            throw new IllegalArgumentException("❌ 노트 정보가 없습니다.");
        }

        if (principal == null || principal.getName() == null) {
            throw new SecurityException("❌ 인증 정보가 없습니다.");
        }

        String userIdFromPrincipal = principal.getName();
        String userIdFromDTO = noteDTO.getUserId();

        if (userIdFromDTO == null || !userIdFromDTO.equals(userIdFromPrincipal)) {
            throw new IllegalArgumentException("❌ 요청한 사용자 정보가 일치하지 않습니다. [" + userIdFromDTO + " != " + userIdFromPrincipal + "]");
        }

        System.out.println("✅ 노트 저장 시도 - 사용자: " + userIdFromPrincipal);
        System.out.println("✅ 노트 데이터: " + noteDTO);

        // DTO -> Entity 변환
        NoteInsert noteEntity;
        try {
            noteEntity = modelMapper.map(noteDTO, NoteInsert.class);
        } catch (Exception e) {
            throw new RuntimeException("❌ DTO 변환 오류: " + e.getMessage());
        }

        // 데이터 검증 (DB 저장 전)
        if (noteEntity.getNoteTitle() == null || noteEntity.getNoteTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ 노트 제목이 없습니다.");
        }
        if (noteEntity.getNoteDetail() == null || noteEntity.getNoteDetail().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ 노트 내용이 없습니다.");
        }
        if (noteEntity.getUserId() == null || noteEntity.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ 사용자 ID가 없습니다.");
        }

        // 노트 저장
        try {
            NoteInsert savedNote = noteInsertRepository.saveAndFlush(noteEntity);
            System.out.println("✅ 노트 저장 완료 - noteCode: " + savedNote.getNoteCode());
            return savedNote.getNoteCode();
        } catch (Exception e) {
            throw new RuntimeException("❌ 노트 저장 중 오류 발생: " + e.getMessage());
        }
    }
}
