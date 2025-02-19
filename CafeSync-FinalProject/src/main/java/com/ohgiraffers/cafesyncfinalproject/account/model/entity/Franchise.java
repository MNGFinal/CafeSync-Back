package com.ohgiraffers.cafesyncfinalproject.account.model.entity;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_franchise")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Franchise {

    @Id
    @Column(name = "fran_code")
    private int franCode;

    @Column(name = "fran_name", nullable = false, length = 255)
    private String franName;

    @Column(name = "fran_addr", length = 255)
    private String franAddr;

    @Column(name = "fran_phone", length = 255)
    private String franPhone;

    @Column(name = "fran_image", length = 255)
    private String franImage;

    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;

    // ✅ ManyToOne 관계 설정 (Employee와 연결)
    @ManyToOne
    @JoinColumn(name = "emp_code")
    private Employee employee; // 담당 직원(Employee) 객체 참조
}
