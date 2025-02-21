package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "입출고 재고 정보 DTO") // ✅ DTO 전체 설명
public class InOutInventoryDTO {

    @Schema(description = "재고 코드", example = "INV-001")
    private String invenCode;

    @Schema(description = "재고명", example = "콜롬비아 원두 1kg")
    private String invenName;

    @Schema(description = "입출고 수량", example = "30")
    private int quantity;
}
