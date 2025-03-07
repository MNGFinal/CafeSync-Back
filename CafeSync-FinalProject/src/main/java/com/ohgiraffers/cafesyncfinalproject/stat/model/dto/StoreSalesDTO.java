package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoreSalesDTO {

    private int franCode;    // 가맹점 코드
    private String franName; // 가맹점 이름 (추가)
    private Long totalSales; // 총 매출 금액
}