package com.ohgiraffers.cafesyncfinalproject.franchise.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FranDTO {

    private int franCode;   // 가맹점 코드
    private String franName;   // 가맹점 이름
    private String franAddr;   // 가맹점 주소
    private String franPhone;  // 가맹점 전화번호
    private String franImage;  // 가맹점 이미지
    private String memo;       // 가맹점 특이사항
    private int empCode;    // 직원 번호 (점장Code)

}
