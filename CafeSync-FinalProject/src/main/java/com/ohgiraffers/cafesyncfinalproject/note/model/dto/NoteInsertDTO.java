package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import lombok.*;

import java.time.LocalDateTime;

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
}
