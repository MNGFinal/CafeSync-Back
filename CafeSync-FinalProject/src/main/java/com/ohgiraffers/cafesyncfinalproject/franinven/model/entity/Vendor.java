package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_vendor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vendor {

    @Id
    @Column(name = "ven_code")
    private Integer venCode; // 공급업체 코드 (PK)

    @Column(name = "ven_name", nullable = false, length = 255)
    private String venName; // 공급업체 이름

    @Column(name = "business_num", nullable = false, length = 12)
    private String businessNum; // 사업자 등록번호

    @Column(name = "ven_image", length = 255)
    private String venImage; // 공급업체 이미지

    @Column(name = "ven_owner", length = 255)
    private String venOwner; // 대표자명

    @Column(name = "ven_addr", length = 255)
    private String venAddr; // 공급업체 주소

    @Column(name = "ven_division", length = 10)
    private String venDivision; // 공급업체 구분
}
