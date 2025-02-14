package com.ohgiraffers.cafesyncfinalproject.home.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "FindAccount")
@Table(name = "tbl_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Account {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pass")
    private String userPass;

    @Column(name = "emp_code")
    private int empCode;

    @Column(name = "email")
    private String email;

    @Column(name = "authority")
    private int authority;

    @Column(name = "job_code")
    private int jobCode;

    @Column(name = "store_code")
    private int storeCode;
}
