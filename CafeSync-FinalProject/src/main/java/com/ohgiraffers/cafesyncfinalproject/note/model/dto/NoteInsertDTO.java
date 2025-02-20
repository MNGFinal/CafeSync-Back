package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoteInsertDTO {

    private int noteCode;
    private String noteTitle;
    private LocalDateTime noteDate;
    private String noteDetail;
    private String attachment;
    private String userId;

    public void setNoteDateToKST() {
        this.noteDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // ✅ 한국 시간 적용
    }
}
