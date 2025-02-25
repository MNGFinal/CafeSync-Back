package com.ohgiraffers.cafesyncfinalproject.slip.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_tax")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Tax {

    @Id
    @Column(name = "tax_id", length = 255)
    private String taxId;     // VARCHAR(255) PK

    @Column(name = "tax_date", nullable = false)
    private LocalDate taxDate;  // DATE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slip_code", nullable = false)
    private Slip slip;

    @Column(name = "tax_val", nullable = false)
    private int taxVal;         // INT

    @Column(name = "fran_code", nullable = false)
    private int franCode;       // INT
}
