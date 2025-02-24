package com.ohgiraffers.cafesyncfinalproject.summary.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_summary")  // 실제 테이블명이 tbl_summary라면
public class Summary {

    @Id
    @Column(name = "summary_code", nullable = false, length = 255)
    private String summaryCode;

    @Column(name = "summary_name", nullable = false, length = 255)
    private String summaryName;
}
