package com.ohgiraffers.cafesyncfinalproject.slip.model.dao;

import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.PnlSlip;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PnlSlipRepository extends JpaRepository<PnlSlip, Integer> {


    // ✅ `pnlId` 타입을 `String`으로 변경 (기존 Integer에서 수정)
    @Query("SELECT DISTINCT ps.pnl.pnlId FROM PnlSlip ps WHERE ps.slip.slipCode IN :slipCodes")
    List<String> findPnlIdsBySlipCodes(@Param("slipCodes") List<Integer> slipCodes);

    // ✅ 전표 삭제 전 관련 PnlSlip 삭제 (JPQL 수정)
    @Modifying
    @Transactional
    @Query("DELETE FROM PnlSlip ps WHERE ps.slip.slipCode IN :slipCodes")
    void deleteBySlip_SlipCodeIn(@Param("slipCodes") List<Integer> slipCodes);

    // ✅ `pnlId` 타입을 `String`으로 변경
    @Query("SELECT SUM(ps.slip.credit) FROM PnlSlip ps WHERE ps.pnl.pnlId = :pnlId")
    Integer getUpdatedRevenue(@Param("pnlId") String pnlId);

    @Query("SELECT SUM(ps.slip.debit) FROM PnlSlip ps WHERE ps.pnl.pnlId = :pnlId")
    Integer getUpdatedExpense(@Param("pnlId") String pnlId);
}

