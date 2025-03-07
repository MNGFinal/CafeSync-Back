package com.ohgiraffers.cafesyncfinalproject.notice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_notice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_code")
    private int noticeCode;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "notice_date")
    private LocalDateTime noticeDate;

    @Column(name = "notice_views")
    private int noticeViews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "attachment", columnDefinition = "LONGTEXT")
    private String attachment;


    // ✅ 조회수 증가 메서드 추가
    public void increaseViewCount() {
        this.noticeViews += 1;
    }


}
