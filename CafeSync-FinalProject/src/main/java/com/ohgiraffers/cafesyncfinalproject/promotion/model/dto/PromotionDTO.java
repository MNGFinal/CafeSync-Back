package com.ohgiraffers.cafesyncfinalproject.promotion.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "프로모션 DTO")
public class PromotionDTO {

    @Schema(description = "프로모션 코드")
    private int promotionCode;

    @Schema(description = "프로모션 시작일")
    private Date startDate;

    @Schema(description = "프로모션 종료일")
    private Date endDate;

    @Schema(description = "카테고리명", example = "콜라보, 이벤트, 시즌")
    private String categoryName;

    @Schema(description = "프로모션 제목")
    private String title;

    @Schema(description = "메모")
    private String memo;

}
