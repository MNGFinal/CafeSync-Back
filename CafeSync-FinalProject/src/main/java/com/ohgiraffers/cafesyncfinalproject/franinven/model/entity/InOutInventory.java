package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_inout_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InOutInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ 자동 증가(AUTO_INCREMENT)
    @Column(name = "id")
    private int id;

    @Column(name = "inout_code", nullable = false) // ✅ 외래키 (tbl_inout의 inout_code)
    private int inoutCode;

    @Column(name = "inven_code", nullable = false, length = 255) // ✅ 제품 코드
    private String invenCode;

    @Column(name = "quantity", nullable = false) // ✅ 출고 수량
    private Integer  quantity;

    public InOutInventory(int inoutCode, String invenCode, Integer quantity) {
        this.inoutCode = inoutCode;
        this.invenCode = invenCode;
        this.quantity = quantity;
    }

}
