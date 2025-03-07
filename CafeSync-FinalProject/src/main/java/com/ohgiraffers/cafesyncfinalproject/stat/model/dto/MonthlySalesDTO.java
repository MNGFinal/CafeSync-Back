package com.ohgiraffers.cafesyncfinalproject.stat.model.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class MonthlySalesDTO {
    private String month;
    private Long sales;

    public MonthlySalesDTO(String month, Long sales) {
        this.month = month;
        this.sales = sales;
    }

    public String getMonth() {
        return month;
    }

    public Long getSales() {
        return sales;
    }
}