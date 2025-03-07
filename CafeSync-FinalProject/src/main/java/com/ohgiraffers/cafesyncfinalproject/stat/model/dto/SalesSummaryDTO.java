package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SalesSummaryDTO {
    private SalesDataDTO today;
    private SalesDataDTO week;
    private SalesDataDTO month;
    private SalesDataDTO year;
    private List<MonthlySalesDTO> monthlySales; // ✅ 추가된 필드

    // ✅ 직접 생성자 정의 (lombok @AllArgsConstructor 삭제)
    public SalesSummaryDTO(SalesDataDTO today, SalesDataDTO week, SalesDataDTO month, SalesDataDTO year, List<MonthlySalesDTO> monthlySales) {
        this.today = today;
        this.week = week;
        this.month = month;
        this.year = year;
        this.monthlySales = monthlySales;
    }
}
