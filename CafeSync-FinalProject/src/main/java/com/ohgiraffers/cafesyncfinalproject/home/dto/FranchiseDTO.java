package com.ohgiraffers.cafesyncfinalproject.home.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FranchiseDTO {

    private int franCode;
    private String franName;
    private String franAddr;
    private String franPhone;
    private String franImage;
    private String memo;
    private int empCode;
}
