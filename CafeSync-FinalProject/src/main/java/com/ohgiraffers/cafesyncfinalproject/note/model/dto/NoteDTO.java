package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoteDTO {

    private int noteCode;
    private String noteTitle;
    private LocalDateTime noteDate;
    private String noteDetail;
    private String attachment;
    private String userId;
    private String empName;
}
