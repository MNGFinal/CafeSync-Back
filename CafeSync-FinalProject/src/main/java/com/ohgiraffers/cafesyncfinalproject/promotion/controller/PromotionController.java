package com.ohgiraffers.cafesyncfinalproject.promotion.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.dto.PromotionDTO;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.service.PromotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hq")
@RequiredArgsConstructor
@Tag(name = "PromotionController", description = "본사 - 프로모션")
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/promotions")
    public ResponseEntity<ResponseDTO> findPromotions() {
        List<PromotionDTO> promotions = promotionService.findPromotions();
        if(promotions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "프로모션을 찾을 수 없음", null));
        }
        System.out.println("promotions = " + promotions);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "프로모션 조회 성공", promotions));
    }

    @PostMapping("/promotion")
    public ResponseEntity<ResponseDTO> registPromotion(@RequestBody PromotionDTO promotion) {
        if (promotion == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "유효하지 않은 형식으로 잘못된 요청", null));
        }
        PromotionDTO savedPromotion = promotionService.savePromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED, "프로모션 등록 성공", savedPromotion));
    }

}
