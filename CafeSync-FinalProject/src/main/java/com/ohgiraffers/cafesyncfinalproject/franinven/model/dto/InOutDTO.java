package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "입출고 내역 DTO") // ✅ DTO 전체 설명
public class InOutDTO {

    @Schema(description = "입출고 코드 (PK)", example = "5001")
    private int inoutCode;

    @Schema(description = "입출고 구분 (1: 입고, 2: 출고)", example = "1")
    private int inoutDivision;

    @Schema(description = "입출고 날짜", example = "2024-02-21T15:30:00")
    private LocalDateTime inoutDate;

    @Schema(description = "입출고 상태 (0: 대기, 1: 승인)", example = "1")
    private int inoutStatus;

    @Schema(description = "출고 가맹점 정보", implementation = FranchiseDTO.class)
    private FranchiseDTO franOutCode;

    @Schema(description = "입고 가맹점 정보", implementation = FranchiseDTO.class)
    private FranchiseDTO franInCode;

    @Schema(description = "입출고된 재고 목록", implementation = InOutInventoryDTO.class)
    private List<InOutInventoryDTO> inventoryList;
}
