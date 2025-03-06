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

    // 날짜별 통계
    @Query(value = "SELECT DATE_FORMAT(s.sales_date, '%Y-%m') AS month, SUM(s.sales_amount) " +
            "FROM tbl_sales s " +
            "WHERE (:startDate IS NULL OR s.sales_date >= :startDate) " +
            "AND (:endDate IS NULL OR s.sales_date <= :endDate) " +
            "AND s.fran_code = :franCode " +
            "GROUP BY month " +
            "ORDER BY month ASC",
            nativeQuery = true)
    List<Object[]> getSalesByDateRange(Integer franCode, LocalDate startDate, LocalDate endDate);

    // 메뉴별 통계
    // ✅ 특정 가맹점의 메뉴별 판매 개수를 조회하는 쿼리
    @Query(value = """
        SELECT m.menu_name_ko AS menuName, COUNT(s.menu_code) AS totalSales
        FROM tbl_sales s
        JOIN tbl_menu m ON s.menu_code = m.menu_code
        WHERE s.fran_code = :franCode
        AND s.sales_date BETWEEN :startDate AND :endDate
        GROUP BY m.menu_name_ko
        ORDER BY totalSales DESC
    """, nativeQuery = true)
    List<Object[]> getMenuSalesStats(
            @Param("franCode") Integer franCode,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}





