package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.Vendor;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InventoryDTO {
    
    private String invenCode; // 거래처별 제품 코드
    private String invenName; // 거래처별 제품 이름
    private LocalDateTime expirationDate; // 제품 유통기한
    private String invenImage; // 제품 이미지
    private VendorDTO vendor; // 거래처 코드
}
