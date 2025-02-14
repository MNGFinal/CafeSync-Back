package com.ohgiraffers.cafesyncfinalproject.home.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
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
