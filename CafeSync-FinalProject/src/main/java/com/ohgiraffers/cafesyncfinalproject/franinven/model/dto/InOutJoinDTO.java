package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InOutJoinDTO {

    private int id;
    private int inOutCode;
    private String invenCode;
    private Integer quantity;
}
