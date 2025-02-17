package com.ohgiraffers.cafesyncfinalproject.franinven.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name= "FranInvenJoin")
@Table(name = "tbl_inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Inventory {

    @Id
    @Column(name = "inven_code", nullable = false, length = 255)
    private String invenCode; // ✅ PK 설정

    @Column(name = "inven_name", nullable = false, length = 255)
    private String invenName;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "inven_image", length = 255)
    private String invenImage;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정 (지연 로딩 적용)
    @JoinColumn(name = "ven_code", referencedColumnName = "ven_code", nullable = false)
    private Vendor vendor; // ✅ 공급업체 정보 (연결된 엔티티)
}
