package com.ohgiraffers.cafesyncfinalproject.complain.model.Entity;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_complain")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Complain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complain_code")
    private int complainCode;

    @Column(name = "complain_division")
    private int complainDivision;

    @Column(name = "complain_date")
    private LocalDateTime complainDate;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "complain_detail")
    private String complainDetail;

    @Column(name = "fran_code")
    private int franCode;

    @Column(name = "emp_code")
    private int empCode;

    // JOIN
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_code", referencedColumnName = "emp_code", insertable = false, updatable = false)
    private Employee employee;

}
