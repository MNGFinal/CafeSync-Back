package com.ohgiraffers.cafesyncfinalproject.summary.model.dao;

import com.ohgiraffers.cafesyncfinalproject.summary.model.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryRepository extends JpaRepository<Summary, String> {
}
