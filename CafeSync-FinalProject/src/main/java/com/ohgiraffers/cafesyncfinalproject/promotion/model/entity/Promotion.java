package com.ohgiraffers.cafesyncfinalproject.promotion.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tbl_promotion")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_code")
    private int promotionCode;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "title")
    private String title;

    @Column(name = "memo")
    private String memo;

}
