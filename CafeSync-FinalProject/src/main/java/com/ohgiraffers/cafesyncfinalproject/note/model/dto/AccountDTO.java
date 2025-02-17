package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String userId;
    private String userPass;
    private int empCode;
    private String email;
    private int authority;
    private int jobCode;
    private int storeCode;
}
