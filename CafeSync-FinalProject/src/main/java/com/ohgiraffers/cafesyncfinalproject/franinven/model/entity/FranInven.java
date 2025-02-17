package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tbl_franinven")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FranInven {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "franinven_code")
    private int franInvenCode; // 재고 PK

    @Column(name = "stock_qty", nullable = false)
    private int stockQty; // 보유 수량

    @Column(name = "order_qty", nullable = false)
    private int orderQty; // 발주 수량

    @Column(name = "recomm_qty", nullable = false)
    private int recommQty; // 권장 수량

    @Column(name = "last_in")
    @Temporal(TemporalType.DATE)
    private Date lastIn; // 최근 입고 일자

    @Column(name = "confirmed", columnDefinition = "TINYINT(1)")
    private int confirmed; // 확인 여부 (0 또는 1)

    @ManyToOne(fetch = FetchType.LAZY) // ✅ ManyToOne 설정 (연관 관계 추가)
    @JoinColumn(name = "inven_code", referencedColumnName = "inven_code", nullable = false)
    private Inventory inventory; // ✅ Inventory 객체 추가

    @Column(name = "fran_code", nullable = false)
    private int franCode; // 가맹점 코드
}
