package com.ohgiraffers.cafesyncfinalproject.promotion.model.dao;

import com.ohgiraffers.cafesyncfinalproject.promotion.model.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}
