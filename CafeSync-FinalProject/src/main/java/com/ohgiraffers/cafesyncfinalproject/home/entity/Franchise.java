package com.ohgiraffers.cafesyncfinalproject.home.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "FranList")
@Table(name = "tbl_franchise")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Franchise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 적용
    @Column(name = "fran_code")
    private int franCode;

    @Column(name = "fran_name")
    private String franName;

    @Column(name = "fran_addr")
    private String franAddr;

    @Column(name = "fran_phone")
    private String franPhone;

    @Column(name = "fran_image")
    private String franImage;

    @Column(name = "memo")
    private String memo;

    @Column(name = "emp_code")
    private int empCode;
}
