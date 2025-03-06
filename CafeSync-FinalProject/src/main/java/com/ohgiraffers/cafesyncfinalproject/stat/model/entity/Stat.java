package com.ohgiraffers.cafesyncfinalproject.stat.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "tbl_sales")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_code")
    private int salesCode;

    @Column(name = "sales_date")
    private Date salesDate;

    @Column(name = "sales_amount")
    private int salesAmount;

    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "fran_code")
    private int franCode;

}
