package com.ohgiraffers.cafesyncfinalproject.vendor.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "findVendor")
@Table(name = "tbl_vendor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ven_code")
    private int venCode;  // 기본 키, auto increment

    @Column(name = "ven_name", length = 255, nullable = false)
    private String venName;

    @Column(name = "business_num", length = 12)
    private String businessNum;

    @Column(name = "ven_image", length = 255)
    private String venImage;

    @Column(name = "ven_owner", length = 255)
    private String venOwner;

    @Column(name = "ven_addr", length = 255)
    private String venAddr;

    @Column(name = "ven_division", length = 10)
    private String venDivision;
}
