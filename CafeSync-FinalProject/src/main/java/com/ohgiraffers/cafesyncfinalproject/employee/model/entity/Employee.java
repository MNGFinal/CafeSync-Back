package com.ohgiraffers.cafesyncfinalproject.employee.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tbl_employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 적용
    @Column(name = "emp_code")
    private int empCode;

    @Column(name = "emp_name", nullable = false, length = 255)
    private String empName;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "addr", length = 255)
    private String address;

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE) // 날짜 타입 지정
    private Date hireDate;

    @Column(name = "retire_date")
    @Temporal(TemporalType.DATE)
    private Date retireDate;

    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;

    @Column(name = "job_code")
    private int jobCode;

    @Column(name = "salary_unit")
    private Integer salaryUnit;

    @Column(name = "salary")
    private int salary;

    @Column(name = "fran_code")
    private Integer franCode;
}
