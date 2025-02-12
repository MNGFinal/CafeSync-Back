package com.ohgiraffers.cafesyncfinalproject.menu.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MenuDTO {

    private int menuCode;           // 메뉴 코드
    private String menuNameKo;      // 메뉴 이름(한글)
    private String menuNameEN;      // 메뉴 이름(영어)
    private String menuDetail;      // 메뉴 설명
    private String menuImage;       // 메뉴 이미지
    private Boolean orderableStatus;// 판매여부
    private int categoryCode;       // 카테고리 코드
    private Boolean disconStatus;   // 단종 여부
    private int seasonMenu;         // 시즌 메뉴

}
