package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "노트 정보 DTO")
public class NoteInsertDTO {

    @Schema(description = "노트 코드 (PK)", example = "1")
    private int noteCode;

    @Schema(description = "노트 제목", example = "맛있는 빵 만드는 노하우")
    private String noteTitle;

    @Schema(description = "노트 날짜", example = "2025-02-21 01:00:00")
    private LocalDateTime noteDate;

    @Schema(description = "노트 내용", example = "시간과 정성을 담아 빵을 만들어요")
    private String noteDetail;

    @Schema(description = "노트 첨부", example = "빵 만드는 노하우.pdf")
    private String attachment;

    @Schema(description = "노트 생성자 ID", example = "user001")
    private String userId;

    public void setNoteDateToKST() {
        this.noteDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // ✅ 한국 시간 적용
    }
}
