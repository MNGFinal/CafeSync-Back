package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Schema(description = "발주 정보 DTO")
public class OrderDTO {

    @Schema(description = "발주 번호 (PK)", example = "1001")
    private int orderCode;

    @Schema(description = "가맹점 코드", example = "101")
    private int franCode;

    @Schema(description = "가맹점 명", example = "카페101")
    private String franName; // 추가된 필드

    @Schema(description = "발주 신청 날짜", example = "2024-02-21T15:30:00")
    private Date orderDate;

    @Schema(description = "발주 상태 (0: 대기, 1: 승인, 2: 반려)", example = "1")
    private int orderStatus;

    @Schema(description = "발주 상세 리스트", implementation = OrderDetailDTO.class)
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO(int orderCode, int franCode, String franName, Date orderDate, int orderStatus) {
        this.orderCode = orderCode;
        this.franCode = franCode;
        this.franName = franName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
