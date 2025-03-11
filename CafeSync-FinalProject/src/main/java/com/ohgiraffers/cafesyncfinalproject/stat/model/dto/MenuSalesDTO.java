package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuSalesDTO {
    private String menuName;  // ✅ 메뉴 이름
    private long sales;  // ✅ 판매 개수
}