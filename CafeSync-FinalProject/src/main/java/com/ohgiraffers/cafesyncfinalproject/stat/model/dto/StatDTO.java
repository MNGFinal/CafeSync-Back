package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StatDTO {

    private int salesCode;      // 매출번호
    private Date salesDate;     // 날짜
    private int salesAmount;    // 매출금액
    private int menuCode;       // 메뉴코드
    private int franCode;       // 가맹점코드

}
