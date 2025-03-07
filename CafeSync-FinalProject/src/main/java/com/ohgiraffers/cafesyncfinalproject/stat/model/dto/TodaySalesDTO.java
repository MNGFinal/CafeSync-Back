package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TodaySalesDTO {

    private int franCode;    // 가맹점 코드
    private String franName; // 가맹점 이름 (추가)
    private Long todaySales; // 오늘의 매출액
    private Date salesDate;  // 매출 날짜
}
