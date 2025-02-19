package com.ohgiraffers.cafesyncfinalproject.schedule.model.entity;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tbl_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_code")
    private int scheduleCode;

    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "emp_code")
    private int empCode;

    @Column(name = "schedule_division")
    private int scheduleDivision;

    @Column(name = "fran_code")
    private int franCode;

    // Join
    @ManyToOne
    @JoinColumn(
            name = "emp_code",
            referencedColumnName = "emp_code",
            insertable = false,
            updatable = false)
    private Employee employee;

}
