package com.ohgiraffers.cafesyncfinalproject.note.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_note")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteUpdate {

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

    // ✅ userId를 직접 저장하는 대신 Account 엔티티와 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Account account;

    public NoteUpdate noteCode(int noteCode) {
        this.noteCode = noteCode;
        return this;
    }

    public NoteUpdate noteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
        return this;
    }

    public NoteUpdate noteDate(LocalDateTime noteDate) {
        this.noteDate = noteDate;
        return this;
    }

    public NoteUpdate noteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
        return this;
    }

    public NoteUpdate attachment(String attachment) {
        this.attachment = attachment;
        return this;
    }

    public NoteUpdate account(Account account) {
        this.account = account;
        return this;
    }

    public NoteUpdate build() {
        return new NoteUpdate(noteCode, noteTitle, noteDate, noteDetail, attachment, account);
    }
}

