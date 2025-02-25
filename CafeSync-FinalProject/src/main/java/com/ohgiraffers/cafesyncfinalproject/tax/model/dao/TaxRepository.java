package com.ohgiraffers.cafesyncfinalproject.tax.model.dao;

import com.ohgiraffers.cafesyncfinalproject.tax.model.dto.TaxDTO;
import com.ohgiraffers.cafesyncfinalproject.tax.model.entity.Tax;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taxRepository2")
public interface TaxRepository extends JpaRepository<Tax, String> {

    @Query(value =
            "SELECT t.tax_id, t.tax_date, t.slip_code, t.tax_val, t.fran_code, " +
                    "       s.slip_date, v.ven_code, v.ven_name, v.business_num, v.ven_image, v.ven_owner, v.ven_addr, v.ven_division, " +
                    "       s.slip_division, " +
                    "       a.act_code, a.act_name, a.act_division, " +
                    "       sm.summary_code, sm.summary_name, " +
                    "       s.debit, s.credit, " +
                    "       f.fran_name, f.fran_addr " +  // ✅ 가맹점 정보 추가
                    "FROM tbl_tax t " +
                    "LEFT JOIN tbl_slip s ON t.slip_code = s.slip_code " +
                    "LEFT JOIN tbl_vendor v ON s.ven_code = v.ven_code " +
                    "LEFT JOIN tbl_act a ON s.act_code = a.act_code " +
                    "LEFT JOIN tbl_summary sm ON s.summary_code = sm.summary_code " +
                    "LEFT JOIN tbl_franchise f ON t.fran_code = f.fran_code " +  // ✅ 가맹점 정보 조인
                    "WHERE t.fran_code = :franCode " +
                    "AND t.tax_date BETWEEN :startDate AND :endDate " +
                    "ORDER BY t.tax_date DESC",  // ✅ 최신 날짜가 먼저 오도록 정렬 추가
            nativeQuery = true)
    List<Object[]> findByFranCodeAndDateRange(
            @Param("franCode") int franCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
