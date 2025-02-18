package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountDTO {

    private String userId;
    private String userPass;
    private int empCode;
    private String email;
    private int authority;
    private int jobCode;
    private int storeCode;
}
