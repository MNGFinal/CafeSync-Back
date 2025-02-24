package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailDTO {

    private int orderDetailId; // 발주 상세 번호
    private int orderCode; // 발주 신청 번호
    private String invenCode; // 제품 코드
    private int orderQty; // 발주 수량

    private InventoryDTO inventory;
}
