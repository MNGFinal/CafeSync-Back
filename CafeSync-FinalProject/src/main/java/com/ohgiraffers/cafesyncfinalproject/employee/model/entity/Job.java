package com.ohgiraffers.cafesyncfinalproject.employee.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "jobJoin")
@Table(name = "tbl_job")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Job {

    @Id
    @Column(name = "job_code")
    private int jobCode;

    @Column(name = "job_name")
    private String jobName;
}
