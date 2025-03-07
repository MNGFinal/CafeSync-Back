package com.ohgiraffers.cafesyncfinalproject.stat.model.dao;

import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.MenuSalesDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.MonthlySalesDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.StoreSalesDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.TodaySalesDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.entity.Stat;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    // ✅ 특정 가맹점의 메뉴별 판매 개수를 조회하는 쿼리
    @Query(value = """
        SELECT m.menu_name_ko AS menuNameKo, COUNT(s.menu_code) AS totalSales
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







    // ✅ 가맹점별 매출 순위 (TOP 5) - Franchise 테이블과 JOIN
    @Query("SELECT new com.ohgiraffers.cafesyncfinalproject.stat.model.dto.StoreSalesDTO(f.franCode, f.franName, SUM(s.salesAmount)) " +
            "FROM Stat s JOIN Fran f ON s.franCode = f.franCode " +
            "WHERE s.salesDate BETWEEN :startDate AND :endDate " +
            "GROUP BY f.franCode, f.franName " +
            "ORDER BY SUM(s.salesAmount) DESC")
    List<StoreSalesDTO> findTopStoresBySales(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    // ✅ 메뉴별 판매 순위 (TOP 10) - Menu 테이블과 JOIN
    @Query("SELECT new com.ohgiraffers.cafesyncfinalproject.stat.model.dto.MenuSalesDTO(m.menuNameKo, CAST(COUNT(s.menuCode) AS int)) " +
            "FROM Stat s JOIN Menu m ON s.menuCode = m.menuCode " +
            "WHERE s.salesDate BETWEEN :startDate AND :endDate " +
            "GROUP BY m.menuNameKo " +
            "ORDER BY COUNT(s.menuCode) DESC")
    List<MenuSalesDTO> findTopMenusBySales(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    // ✅ 오늘의 가맹점 매출 순위 (TOP 5) - Franchise 테이블과 JOIN
    @Query("SELECT new com.ohgiraffers.cafesyncfinalproject.stat.model.dto.TodaySalesDTO(f.franCode, f.franName, SUM(s.salesAmount), s.salesDate) " +
            "FROM Stat s JOIN Franchise f ON s.franCode = f.franCode " +
            "WHERE s.salesDate = :today " +
            "GROUP BY f.franCode, f.franName, s.salesDate " +
            "ORDER BY SUM(s.salesAmount) DESC")
    List<TodaySalesDTO> findTodaySalesByStore(@Param("today") Date today);


    @Query("SELECT new com.ohgiraffers.cafesyncfinalproject.stat.model.dto.MonthlySalesDTO(" +
            "CONCAT(YEAR(s.salesDate), '-', CASE WHEN MONTH(s.salesDate) < 10 THEN CONCAT('0', MONTH(s.salesDate)) ELSE MONTH(s.salesDate) END), " +
            "SUM(s.salesAmount)) " +
            "FROM Stat s " +
            "WHERE s.salesDate BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(s.salesDate), MONTH(s.salesDate) " +
            "ORDER BY YEAR(s.salesDate), MONTH(s.salesDate)")
    List<MonthlySalesDTO> findMonthlySales(@Param("startDate") Date startDate, @Param("endDate") Date endDate);













}





