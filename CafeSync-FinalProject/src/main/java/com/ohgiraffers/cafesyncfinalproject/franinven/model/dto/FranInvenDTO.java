package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.dto.InvenDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "가맹점 재고 정보 DTO") // ✅ DTO 전체 설명
public class FranInvenDTO {

    @Schema(description = "가맹점 재고 코드 (PK)", example = "1001")
    private int franInvenCode;

    @Schema(description = "보유 수량", example = "50")
    private int stockQty;

    @Schema(description = "발주 수량", example = "30")
    private int orderQty;

    @Schema(description = "권장 발주 수량", example = "40")
    private int recommQty;

    @Schema(description = "최근 입고 일자", example = "2024-02-19T15:30:00")
    private Date lastIn;

    @Schema(description = "확인 여부 (0: 미확인, 1: 확인)", example = "1")
    private int confirmed;

    @Schema(description = "재고 정보", implementation = InvenDTO.class)
    private InventoryDTO inventory;

    @Schema(description = "가맹점 코드", example = "101")
    private int franCode;
}
