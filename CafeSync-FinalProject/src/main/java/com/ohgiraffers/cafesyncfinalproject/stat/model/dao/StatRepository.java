package com.ohgiraffers.cafesyncfinalproject.stat.model.dao;

import com.ohgiraffers.cafesyncfinalproject.stat.model.entity.Stat;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Stat, Integer> {

    List<Stat> findByFranCode(Integer franCode); // 특정 가맹점 매출 조회

    // 오늘 매출 (특정 가맹점)
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE s.salesDate = CURRENT_DATE AND s.franCode = :franCode")
    List<Object[]> getTodaySalesByFranCode(Integer franCode);

    // 이번 주 매출 (특정 가맹점)
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE WEEK(s.salesDate) = WEEK(CURRENT_DATE) AND YEAR(s.salesDate) = YEAR(CURRENT_DATE) AND s.franCode = :franCode")
    List<Object[]> getWeeklySalesByFranCode(Integer franCode);

    // 이번 달 매출 (특정 가맹점)
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE MONTH(s.salesDate) = MONTH(CURRENT_DATE) AND YEAR(s.salesDate) = YEAR(CURRENT_DATE) AND s.franCode = :franCode")
    List<Object[]> getMonthlySalesByFranCode(Integer franCode);

    // 올해 매출 (특정 가맹점)
    @Query("SELECT COALESCE(SUM(s.salesAmount), 0), COUNT(s) FROM Stat s WHERE YEAR(s.salesDate) = YEAR(CURRENT_DATE) AND s.franCode = :franCode")
    List<Object[]> getYearlySalesByFranCode(Integer franCode);


    @Query(value = "SELECT DATE_FORMAT(s.sales_date, '%Y-%m') AS month, SUM(s.sales_amount) " +
            "FROM tbl_sales s " +
            "WHERE (:startDate IS NULL OR s.sales_date >= :startDate) " +
            "AND (:endDate IS NULL OR s.sales_date <= :endDate) " +
            "AND s.fran_code = :franCode " +
            "GROUP BY month " +
            "ORDER BY month ASC",
            nativeQuery = true)
    List<Object[]> getSalesByDateRange(Integer franCode, LocalDate startDate, LocalDate endDate);

}




