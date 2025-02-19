package com.ohgiraffers.cafesyncfinalproject.account.model.dto;

import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.EmployeeDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserLoginDTO {

    private String userId;
    private String email;
    private int authority;

    private EmployeeDTO employee;  // ✅ Employee 객체 포함
    private JobDTO job;            // ✅ Job 객체 포함
    private FranchiseDTO franchise; // ✅ Franchise 객체 포함
}
