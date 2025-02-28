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
public class NoticeInsert {

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

    @Column(name = "user_id")
    private String userId;

    @Column(name = "attachment")
    private String attachment;

    public NoticeInsert noticeTitle(String noticeTitle){
        this.noticeTitle = noticeTitle;
        return this;
    }

    public NoticeInsert noticeContent(String noticeContent){
        this.noticeContent = noticeContent;
        return this;
    }

    public NoticeInsert noticeDate(LocalDateTime noticeDate) {
        this.noticeDate = noticeDate;
        return this;
    }

    public NoticeInsert noticeViews(int noticeViews){
        this.noticeViews = noticeViews;
        return this;
    }

    public NoticeInsert userId(String userId){
        this.userId = userId;
        return this;
    }

    public NoticeInsert attachment(String attachment){
        this.attachment = attachment;
        return this;
    }

}
