package com.ohgiraffers.cafesyncfinalproject.note.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String storeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_code", referencedColumnName = "emp_code", insertable = false, updatable = false)
    private Employee employee;
}
