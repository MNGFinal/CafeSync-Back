package com.ohgiraffers.cafesyncfinalproject.note.model.service;

import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NoteService(NoteRepository noteRepository, ModelMapper modelMapper){
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
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

    public Note insertNote(NoteDTO noteDTO) {
        // DTO -> Entity 변환
        Note note = new Note();
        note.setNoteTitle(noteDTO.getNoteTitle());
        note.setNoteDate(noteDTO.getNoteDate());
        note.setNoteDetail(noteDTO.getNoteDetail());
        note.setAttachment(noteDTO.getAttachment());

        return noteRepository.save(note);
    }
}
