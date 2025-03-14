package com.ohgiraffers.cafesyncfinalproject.note.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_code")
    private int noteCode;

    @Column(name = "note_title")
    private String noteTitle;

    @Column(name = "note_date")
    @CreationTimestamp
    private LocalDateTime noteDate;

    @Column(name = "note_detail")
    private String noteDetail;

    @Column(name = "attachment", columnDefinition = "LONGTEXT")
    private String attachment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "view_count")
    private int viewCount;

    // ✅ 조회수 증가 메서드 추가
    public void increaseViewCount() {
        this.viewCount += 1;
    }

}
