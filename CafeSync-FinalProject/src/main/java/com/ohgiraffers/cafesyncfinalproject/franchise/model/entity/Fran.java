package com.ohgiraffers.cafesyncfinalproject.franchise.model.entity;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "tbl_franchise")
public class Fran {

    @Id
    @Column(name = "fran_code")
    private int franCode;

    @Column(name = "fran_name")
    private String franName;

    @Column(name = "fran_addr")
    private String franAddr;

    @Column(name = "fran_phone")
    private String franPhone;

    @Column(name = "fran_image")
    private String franImage;

    @Column(name = "memo")
    private String memo;

    @Column(name = "emp_code")
    private int empCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_code", referencedColumnName = "emp_code", insertable = false, updatable = false)
    private Employee employee;




    /* ✅ Setter 스타일 Builder 패턴 적용 */
    public Fran franName(String var) {
        this.franName = var;
        return this;
    }

    public Fran franAddr(String var) {
        this.franAddr = var;
        return this;
    }

    public Fran franPhone(String var) {
        this.franPhone = var;
        return this;
    }

    public Fran franImage(String var) {
        this.franImage = var;
        return this;
    }

    public Fran memo(String var) {
        this.memo = var;
        return this;
    }

    public Fran empCode(int var) {
        this.empCode = var;
        return this;
    }

    /* ✅ 모든 필드를 포함한 builder() 메서드 */
    public Fran builder() {
        return new Fran(franCode, franName, franAddr, franPhone, franImage, memo, empCode, employee);
    }
}
