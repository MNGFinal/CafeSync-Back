package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InOutDTO {

    private int inoutCode;         // 입출고 코드 (PK)
    private int inoutDivision;     // 입출고 구분 (1: 입고, 2: 출고)
    private LocalDateTime inoutDate; // 입출고 날짜
    private int inoutStatus;       // 입출고 상태 (예: 0: 대기, 1: 승인)
    private FranchiseDTO franOutCode; // 출고 가맹점
    private FranchiseDTO franInCode;  // 입고 가맹점
    private List<InOutInventoryDTO> inventoryList; // ✅ 여러 개의 재고 목록 포함
}
