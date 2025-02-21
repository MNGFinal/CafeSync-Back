package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "입출고와 재고 연결 DTO") // ✅ DTO 전체 설명
public class InOutJoinDTO {

    @Schema(description = "입출고 재고 연결 ID (PK)", example = "1001")
    private int id;

    @Schema(description = "입출고 코드 (FK)", example = "5001")
    private int inOutCode;

    @Schema(description = "재고 코드", example = "INV-001")
    private String invenCode;

    @Schema(description = "입출고 수량", example = "30")
    private Integer quantity;
}
