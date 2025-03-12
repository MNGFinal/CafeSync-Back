package com.ohgiraffers.cafesyncfinalproject.promotion.model.service;

import com.ohgiraffers.cafesyncfinalproject.promotion.model.dao.PromotionRepository;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.dto.PromotionDTO;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.entity.Promotion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final ModelMapper modelMapper;

    public List<PromotionDTO> findPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(promotion -> modelMapper.map(promotion, PromotionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PromotionDTO savePromotion(PromotionDTO promotion) {
        Promotion registPromotion = modelMapper.map(promotion, Promotion.class);
        Promotion savedPromotion = promotionRepository.save(registPromotion);
        return modelMapper.map(savedPromotion, PromotionDTO.class);
    }

    public PromotionDTO findByPromotionCode(int promotionCode) {
        Promotion findPromotion = promotionRepository.findByPromotionCode(promotionCode);
        if (findPromotion != null) {
            PromotionDTO promotionDTO = modelMapper.map(findPromotion, PromotionDTO.class);
            return promotionDTO;
        } else {
            return null;
        }
    }

    public void deletePromotion(int promotionCode) {
        promotionRepository.deleteById(promotionCode);
    }
}
