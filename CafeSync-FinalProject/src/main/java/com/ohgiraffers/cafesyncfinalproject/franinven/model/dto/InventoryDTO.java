package com.ohgiraffers.cafesyncfinalproject.franinven.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Schema(description = "재고(Inventory) DTO") // ✅ DTO 전체 설명
public class InventoryDTO {

    @Schema(description = "거래처별 제품 코드", example = "INV-001")
    private String invenCode;

    @Schema(description = "거래처별 제품 이름", example = "콜롬비아 원두 1kg")
    private String invenName;

    @Schema(description = "제품 유통기한", example = "2024-12-31T23:59:59")
    private LocalDateTime expirationDate;

    @Schema(description = "제품 이미지 URL", example = "https://example.com/images/coffee.png")
    private String invenImage;

    @Schema(description = "거래처 정보", implementation = VendorDTO.class)
    private VendorDTO vendor;
}
