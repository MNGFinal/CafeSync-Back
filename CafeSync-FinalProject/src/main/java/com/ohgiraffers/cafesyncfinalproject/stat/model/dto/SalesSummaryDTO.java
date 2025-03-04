package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalesSummaryDTO {
    private SalesDataDTO today;
    private SalesDataDTO week;
    private SalesDataDTO month;
    private SalesDataDTO year;
}
