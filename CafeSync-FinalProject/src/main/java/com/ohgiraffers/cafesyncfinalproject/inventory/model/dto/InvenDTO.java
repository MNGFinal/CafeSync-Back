package com.ohgiraffers.cafesyncfinalproject.inventory.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InvenDTO {

    private String invenCode;
    private String invenName;
    private Date expirationDate;
    private String invenImage;
    private int venCode;
}
