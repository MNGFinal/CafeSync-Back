package com.ohgiraffers.cafesyncfinalproject.franchise.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "tbl_franchise")
public class Fran {

    @Id
    @Column(name = "fran_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int franCode;

    @Column(name = "fran_name")
    private int franName;

    @Column(name = "fran_addr")
    private int franAddr;

    @Column(name = "fran_phone")
    private int franPhone;

    @Column(name = "fran_image")
    private int franImage;

    @Column(name = "memo")
    private int memo;

    @Column(name = "emp_code")
    private int empCode;

}
