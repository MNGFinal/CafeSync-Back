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
