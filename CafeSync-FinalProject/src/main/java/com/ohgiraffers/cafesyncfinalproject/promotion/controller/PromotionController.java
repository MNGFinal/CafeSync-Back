package com.ohgiraffers.cafesyncfinalproject.promotion.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.dto.PromotionDTO;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "프로모션 조회",
            description = "프로모션을 전체 조회하여 캘린더에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로모션 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "프로모션을 찾을 수 없음")
            }
    )
    @GetMapping("/promotions")
    public ResponseEntity<ResponseDTO> findPromotions() {
        List<PromotionDTO> promotions = promotionService.findPromotions();
        if(promotions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "프로모션을 찾을 수 없음", null));
        }
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "프로모션 조회 성공", promotions));
    }

    @Operation(
            summary = "프로모션 신규 등록",
            description = "프로모션을 신규 등록하여 캘린더에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "프로모션 등록 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청")
            }
    )
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

    @Operation(
            summary = "프로모션 수정",
            description = "프로모션을 수정하여 캘린더에 표시합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로모션 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @PutMapping("/promotion")
    public ResponseEntity<ResponseDTO> modifyPromotion(@RequestBody PromotionDTO promotion) {
        System.out.println("수정 들어온 promotion = " + promotion);

        if (promotion == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "유효하지 않은 형식으로 잘못된 요청", null));
        }
        PromotionDTO existingPromotion = promotionService.findByPromotionCode(promotion.getPromotionCode());
        if (existingPromotion == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "프로모션을 찾을 수 없음: " + promotion.getPromotionCode(), null));
        }

        existingPromotion.setPromotionCode(promotion.getPromotionCode());
        existingPromotion.setCategoryName(promotion.getCategoryName());
        existingPromotion.setTitle(promotion.getTitle());
        existingPromotion.setStartDate(promotion.getStartDate());
        existingPromotion.setEndDate(promotion.getEndDate());
        existingPromotion.setMemo(promotion.getMemo());

        System.out.println("existingPromotion = " + existingPromotion);

        PromotionDTO savedPromotion = promotionService.savePromotion(existingPromotion);
        System.out.println("savedPromotion = " + savedPromotion);

//        return null;
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "프로모션 수정 성공", savedPromotion));
    }

    @Operation(
            summary = "프로모션 삭제",
            description = "등록된 프로모션을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로모션 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 형식으로 잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "프로모션을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @DeleteMapping("/promotion/{promotionCode}")
    public ResponseEntity<ResponseDTO> deletePromotion(@PathVariable int promotionCode) {
        if (promotionCode <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "유효하지 않은 형식으로 잘못된 요청", null));
        }

        try {
            PromotionDTO deletePromotion = promotionService.findByPromotionCode(promotionCode);
            if (deletePromotion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(HttpStatus.NOT_FOUND, "삭제할 프로모션을 찾을 수 없음", null));
            }

            promotionService.deletePromotion(promotionCode);

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "프로모션 삭제 성공", deletePromotion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", null));
        }
    }

}
