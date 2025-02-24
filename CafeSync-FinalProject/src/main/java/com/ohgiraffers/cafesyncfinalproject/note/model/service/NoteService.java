package com.ohgiraffers.cafesyncfinalproject.note.model.service;

import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteInsertRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dao.NoteUpdateRepository;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.Note;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.NoteInsert;
import com.ohgiraffers.cafesyncfinalproject.note.model.entity.NoteUpdate;
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
    private final NoteUpdateRepository noteUpdateRepository;

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
                                note.getAccount().getEmployee().getEmpName() : null, // empName 가져오기
                        note.getViewCount() // 조회수 추가
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public NoteDTO selectNoteByNoteCode(int noteCode) {
        Note note = noteRepository.findById(noteCode)
                .orElseThrow(() -> new RuntimeException("노트가 존재하지 않습니다."));

        note.increaseViewCount();

        return modelMapper.map(note, NoteDTO.class);
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

        // ✅ 현재 시간을 한국 시간(KST)으로 변환하여 저장
        noteDTO.setNoteDateToKST();

        // DTO -> Entity 변환
        NoteInsert noteEntity;
        noteEntity = modelMapper.map(noteDTO, NoteInsert.class);

        NoteInsert savedNote = noteInsertRepository.saveAndFlush(noteEntity);
        return savedNote.getNoteCode();
    }

    @Transactional
    public Object updateNote(NoteInsertDTO noteInsertDTO) {
        // 기존의 noteCode 로 NoteUpdate 엔티티를 가져오기
        NoteUpdate noteUpdate = noteUpdateRepository.getReferenceById(noteInsertDTO.getNoteCode());

        // ✅ 수정 시간을 현재 한국 시간(KST)으로 변경
        noteInsertDTO.setNoteDateToKST();

        // NoteUpdate 객체의 각 필드 갱신
        noteUpdate = noteUpdate
                .noteTitle(noteInsertDTO.getNoteTitle())
                .noteDate(noteInsertDTO.getNoteDate())
                .noteDetail(noteInsertDTO.getNoteDetail())
                .attachment(noteInsertDTO.getAttachment());

        // 수정된 NoteUpdate 엔티티 저장
        NoteUpdate updatedNote = noteUpdateRepository.save(noteUpdate);

        // 성공 또는 실패 메시지 반환
        return (updatedNote != null) ? "바리스타 노트 업데이트 성공" : "바리스타 노트 업데이트 실패";
    }

    public void deleteNote(int noteCode) {
        noteInsertRepository.deleteById(noteCode);
    }
}
