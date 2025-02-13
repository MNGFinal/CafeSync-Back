package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "바리스타 노트 정보를 담는 DTO")
public class NoteDTO {

    @Schema(description = "노트 NoteCode", example = "1")
    private int noteCode;

    @Schema(description = "노트 noteTitle", example = "커피 제조 노하우")
    private String noteTitle;

    @Schema(description = "노트 noteDate", example = "2025-02-12 11:32:22")
    private LocalDateTime noteDate;

    @Schema(description = "노트 noteDetail", example = "최고의 커피를 만드는 방법.")
    private String noteDetail;

    @Schema(description = "노트 attachment", example = "coffee_guide.pdf")
    private String attachment;

    @Schema(description = "노트 userId", example = "user001")
    private String userId;
}

