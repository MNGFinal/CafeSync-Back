package com.ohgiraffers.cafesyncfinalproject.note.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "직원 정보 DTO") // ✅ DTO 설명 추가
public class EmployeeDTO {

    @Schema(description = "직원 코드 (PK)", example = "3")
    private int empCode;

    @Schema(description = "직원 이름", example = "홍길동")
    private String empName;

    @Schema(description = "프로필 이미지 URL", example = "/images/employees/profile1.jpg")
    private String profileImage;

    @Schema(description = "직원 주소", example = "서울특별시 강남구 테헤란로 123")
    private String addr;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "이메일", example = "hong@example.com")
    private String email;

    @Schema(description = "입사 날짜", example = "2023-01-01")
    private Date hireDate;

    @Schema(description = "퇴사 날짜 (NULL 가능)", example = "null")
    private Date retireDate;

    @Schema(description = "메모", example = "우수 직원")
    private String memo;

    @Schema(description = "직급 코드", example = "21")
    private int jobCode;

    @Schema(description = "급여 단위", example = "1500000")
    private int salaryUnit;

    @Schema(description = "급여", example = "6000000")
    private int salary;

    @Schema(description = "담당 가맹점 코드 (FK)", example = "1000")
    private int franCode;
}

