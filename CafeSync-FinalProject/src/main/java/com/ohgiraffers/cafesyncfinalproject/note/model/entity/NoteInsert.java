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

    @Column(name = "view_count")
    private int viewCount;

    public NoteInsert noteCode(int noteCode) {
        this.noteCode = noteCode;
        return this;
    }

    public NoteInsert noteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
        return this;
    }

    public NoteInsert noteDate(LocalDateTime noteDate) {
        this.noteDate = noteDate;
        return this;
    }

    public NoteInsert noteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
        return this;
    }

    public NoteInsert attachment(String attachment) {
        this.attachment = attachment;
        return this;
    }

    public NoteInsert userId(String userId){
        this.userId = userId;
        return this;
    }
}
