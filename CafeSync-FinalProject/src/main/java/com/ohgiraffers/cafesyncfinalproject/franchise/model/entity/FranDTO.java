package com.ohgiraffers.cafesyncfinalproject.franchise.model.entity;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FranDTO {

    private int franCode;   // 가맹점 코드
    private int franName;   // 가맹점 이름
    private int franAddr;   // 가맹점 주소
    private int franPhone;  // 가맹점 전화번호
    private int franImage;  // 가맹점 이미지
    private int memo;       // 가맹점 특이사항
    private int empCode;    // 직원 번호 (점장Code)

}
