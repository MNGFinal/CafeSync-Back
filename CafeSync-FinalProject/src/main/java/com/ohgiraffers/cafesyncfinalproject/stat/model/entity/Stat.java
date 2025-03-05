package com.ohgiraffers.cafesyncfinalproject.stat.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
