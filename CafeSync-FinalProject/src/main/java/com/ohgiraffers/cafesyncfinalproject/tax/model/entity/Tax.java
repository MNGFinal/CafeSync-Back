package com.ohgiraffers.cafesyncfinalproject.tax.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "taxJoin")
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

    @Column(name = "slip_code", nullable = false)
    private int slipCode;       // INT

    @Column(name = "tax_val", nullable = false)
    private int taxVal;         // INT

    @Column(name = "fran_code", nullable = false)
    private int franCode;       // INT
}
