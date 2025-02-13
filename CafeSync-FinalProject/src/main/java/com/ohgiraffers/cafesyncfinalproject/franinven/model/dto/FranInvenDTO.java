package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FranInvenDTO {

    private int franInvenCode; // 재고 PK
    private int stockQty; // 보유 수량
    private int orderQty; // 발주 수량
    private int recommQty; // 권장 수량
    private Date lastIn; // 최근 입고 일자
    private int confirmed; // 확인 여부 (0 또는 1로 저장)
    private String invenCode; // 재고 코드
    private int franCode; // 가맹점 코드
}
