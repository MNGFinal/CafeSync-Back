package com.ohgiraffers.cafesyncfinalproject.account.model.entity;

import com.ohgiraffers.cafesyncfinalproject.account.model.dto.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pass")
    private String userPass;

    @Column(name = "emp_code")
    private int empCode;

    @Column(name = "email")
    private String email;

    @Convert(converter = Role.RoleConverter.class) // ✅ Enum을 숫자로 변환
    @Column(name = "authority")
    private Role authority;

    @Column(name = "job_code")
    private int jobCode;

    @Column(name = "store_code")
    private int storeCode;
}
