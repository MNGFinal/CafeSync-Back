package com.ohgiraffers.cafesyncfinalproject.act.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_act")
public class Act {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_code")
    private int actCode;

    @Column(name = "act_name", nullable = false, length = 30)
    private String actName;

    @Column(name = "act_division", nullable = false, length = 30)
    private String actDivision;
}
