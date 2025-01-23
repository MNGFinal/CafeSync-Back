package com.ohgiraffers.cafesyncfinalproject.inventory.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "tbl_inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Inventory {

    @Id
    @Column(name = "inven_code")
    private String invenCode;

    @Column(name = "inven_name")
    private String invenName;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "inven_image")
    private String invenImage;

    @Column(name = "ven_code")
    private int venCode;
}
