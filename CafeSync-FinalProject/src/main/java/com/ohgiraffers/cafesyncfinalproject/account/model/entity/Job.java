package com.ohgiraffers.cafesyncfinalproject.account.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
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
