package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InOutInventoryDTO {
    private String invenCode;  // 재고 코드
    private String invenName;  // 재고명
    private int quantity;      // 수량
}
