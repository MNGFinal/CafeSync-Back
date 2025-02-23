package com.ohgiraffers.cafesyncfinalproject.slip.model.dao;

import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Slip;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SlipRepository extends JpaRepository<Slip, Integer> {

    // 가맹점 전표 조회
    @Query(value = """
    SELECT s.slip_code, s.slip_date, v.ven_code, v.ven_name, 
           s.slip_division, a.act_code, a.act_name, a.act_division, 
           su.summary_code, su.summary_name, s.debit, s.credit, s.fran_code
    FROM tbl_slip s
    JOIN tbl_vendor v ON s.ven_code = v.ven_code
    JOIN tbl_act a ON s.act_code = a.act_code
    JOIN tbl_summary su ON s.summary_code = su.summary_code
    WHERE s.fran_code = :franCode
      AND s.slip_date BETWEEN :startDate AND :endDate
    ORDER BY s.slip_date ASC
    """, nativeQuery = true)
    List<Object[]> findFranSlips(@Param("franCode") int franCode,
                                 @Param("startDate") String startDate,
                                 @Param("endDate") String endDate);

}
