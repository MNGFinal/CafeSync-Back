package com.ohgiraffers.cafesyncfinalproject.note.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteInsert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_code")
    private int noteCode;

    @Column(name = "note_title")
    private String noteTitle;

    @Column(name = "note_date")
    private LocalDateTime noteDate;

    @Column(name = "note_detail")
    private String noteDetail;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "user_id")
    private String userId;
}
