package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.InOut;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InOutRepository extends JpaRepository<InOut, Integer> {

    @Query(value = """
    SELECT 
        i.inout_code AS inoutCode,
        i.inout_division AS inoutDivision,
        i.inout_date AS inoutDate,
        i.inout_status AS inoutStatus,
        f1.fran_code AS franOutCode, f1.fran_name AS franOutName, f1.fran_addr AS franOutAddress,
        f2.fran_code AS franInCode, f2.fran_name AS franInName, f2.fran_addr AS franInAddress,
        JSON_ARRAYAGG(
            JSON_OBJECT(
                'invenCode', iv.inven_code,
                'invenName', iv.inven_name,
                'quantity', ii.quantity
            )
        ) AS inventoryList
    FROM tbl_inout i
    LEFT JOIN tbl_franchise f1 ON i.franout_code = f1.fran_code
    LEFT JOIN tbl_franchise f2 ON i.franin_code = f2.fran_code
    LEFT JOIN tbl_inout_inventory ii ON i.inout_code = ii.inout_code
    LEFT JOIN tbl_inventory iv ON ii.inven_code = iv.inven_code
    WHERE i.franin_code = :franCode OR i.franout_code = :franCode
    GROUP BY i.inout_code
    ORDER BY i.inout_date DESC
    """, nativeQuery = true)
    List<Object[]> findInOutWithInventory(@Param("franCode") int franCode);

}
