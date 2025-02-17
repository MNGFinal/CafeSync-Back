package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendorDTO {

    private int venCode; // 거래처 코드
    private String venName; // 거래처명
    private String businessNum; // 사업자번호
    private String venImage; // 거래처 사진
    private String venOwner; // 거래처 대표이름
    private String venAddr; // 거래처 주소
    private String venDivision; // 구분
}
