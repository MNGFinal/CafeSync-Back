package com.ohgiraffers.cafesyncfinalproject.stat.model.dao;

import com.ohgiraffers.cafesyncfinalproject.stat.model.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Stat, Integer> {

    List<Stat> findByFranCode(Integer franCode); // 특정 가맹점 매출 조회

    // 오늘 매출
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE s.salesDate = CURRENT_DATE")
    List<Object[]> getTodaySales();

    // 이번 주 매출
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE WEEK(s.salesDate) = WEEK(CURRENT_DATE) AND YEAR(s.salesDate) = YEAR(CURRENT_DATE)")
    List<Object[]> getWeeklySales();

    // 이번 달 매출
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE MONTH(s.salesDate) = MONTH(CURRENT_DATE) AND YEAR(s.salesDate) = YEAR(CURRENT_DATE)")
    List<Object[]> getMonthlySales();

    // 올해 매출
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE YEAR(s.salesDate) = YEAR(CURRENT_DATE)")
    List<Object[]> getYearlySales();

    }



