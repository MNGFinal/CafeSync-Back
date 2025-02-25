package com.ohgiraffers.cafesyncfinalproject.slip.model.dao;

import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Pnl;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PnlRepository extends JpaRepository<Pnl, String> {

    @Query(value = """
    SELECT
        p.pnl_id, 
        p.period, 
        p.revenue, 
        p.expense, 
        p.oper_profit, 
        p.net_profit, 
        p.ratio, 
        p.fran_code, 
        f.fran_name, 
        f.fran_addr,
        ps.id as pnl_slip_id,
        ps.slip_code, 
        s.slip_date, 
        s.ven_code, 
        s.slip_division, 
        s.act_code, 
        s.summary_code, 
        s.debit, 
        s.credit, 
        v.ven_name, 
        a.act_name, 
        a.act_division, 
        sum.summary_name
    FROM tbl_pnl p
    LEFT JOIN tbl_franchise f ON p.fran_code = f.fran_code
    LEFT JOIN tbl_pnl_slip ps ON p.pnl_id = ps.pnl_id
    LEFT JOIN tbl_slip s ON ps.slip_code = s.slip_code
    LEFT JOIN tbl_vendor v ON s.ven_code = v.ven_code
    LEFT JOIN tbl_act a ON s.act_code = a.act_code
    LEFT JOIN tbl_summary sum ON s.summary_code = sum.summary_code
    WHERE p.fran_code = :franCode
      AND p.period BETWEEN :startDate AND :endDate
    ORDER BY p.period DESC
""", nativeQuery = true)
    List<Object[]> findPnlsWithDetails(
            @Param("franCode") int franCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );



    @Modifying
    @Transactional
    @Query("UPDATE Pnl p " +
            "SET p.revenue = :revenue, " +
            "p.expense = :expense, " +
            "p.netProfit = :netProfit, " +
            "p.ratio = :ratio " +  // ✅ 순이익률 업데이트 추가
            "WHERE p.pnlId = :pnlId")
    void updatePnlAmounts(@Param("pnlId") String pnlId,
                          @Param("revenue") int revenue,
                          @Param("expense") int expense,
                          @Param("netProfit") int netProfit,
                          @Param("ratio") String ratio); // ✅ 순이익률 추가
}
