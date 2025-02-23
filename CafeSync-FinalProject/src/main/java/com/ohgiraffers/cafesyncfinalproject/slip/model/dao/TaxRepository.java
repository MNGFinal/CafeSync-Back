package com.ohgiraffers.cafesyncfinalproject.slip.model.dao;

import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaxRepository extends JpaRepository<Tax, String> {

    // 세금 계산서 PK 값 가져오기
    Tax findFirstByTaxDateOrderByTaxIdDesc(LocalDate taxDate);
}
