package com.ohgiraffers.cafesyncfinalproject.notice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "공지사항 등록 DTO")
public class NoticeInsertDTO {

    @Schema(description = "공지사항 코드 (PK)", example = "1")
    private int noticeCode;

    @Schema(description = "공지사항 제목 ", example = "공지사항 1")
    private String noticeTitle;

    @Schema(description = "공지사항 내용 ", example = "이것은 첫 번째 공지사항 내용입니다. 중요한 내용이니 참고하세요.")
    private String noticeContent;

    @Schema(description = "공지사항 작성시간" , example = "2025-02-24 10:00:00")
    private LocalDateTime noticeDate;

    @Schema(description = "공지사항 조회수" , example = "50")
    private int noticeViews;

    @Schema(description = "공지사항 작성자" , example = "user001")
    private String userId;

    @Schema(description = "공지사항 첨부파일" , example = "주요공지.pdf")
    private String attachment;
}
