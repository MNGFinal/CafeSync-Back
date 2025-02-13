package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FranInvenRepository extends JpaRepository<FranInven, Integer> {

    List<FranInven> findByFranCode(int franCode);
}
