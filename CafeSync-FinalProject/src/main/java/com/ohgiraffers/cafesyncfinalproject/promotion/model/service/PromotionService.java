package com.ohgiraffers.cafesyncfinalproject.promotion.model.service;

import com.ohgiraffers.cafesyncfinalproject.promotion.model.dao.PromotionRepository;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.dto.PromotionDTO;
import com.ohgiraffers.cafesyncfinalproject.promotion.model.entity.Promotion;
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
}
