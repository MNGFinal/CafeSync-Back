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
        NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
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

        // DTO -> Entity 변환
        NoteInsert noteEntity;
        noteEntity = modelMapper.map(noteDTO, NoteInsert.class);

        NoteInsert savedNote = noteInsertRepository.saveAndFlush(noteEntity);
        return savedNote.getNoteCode();
    }

//    public Object updateNote(NoteInsertDTO noteInsertDTO) {
//
//        NoteInsert noteUpdate = noteInsertRepository.findById(noteInsertDTO.getNoteCode()).get();
//
//        noteUpdate = note
//    }
}
