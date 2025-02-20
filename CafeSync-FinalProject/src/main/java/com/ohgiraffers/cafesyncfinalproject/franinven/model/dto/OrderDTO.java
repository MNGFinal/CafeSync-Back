package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {

    private int orderCode; // 발주 번호
    private int franCode; // 가맹점 코드
    private Date orderDate; // 발주 신청 날짜
    private int orderStatus; // 발주 상태
    private List<OrderDetailDTO> orderDetails; // 발주 상세 리스트
}
