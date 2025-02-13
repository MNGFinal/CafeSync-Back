package com.ohgiraffers.cafesyncfinalproject.menu.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private int categoryCode;     // 카테고리 코드
    private String categoryName;  // 카테고리 이름

}
