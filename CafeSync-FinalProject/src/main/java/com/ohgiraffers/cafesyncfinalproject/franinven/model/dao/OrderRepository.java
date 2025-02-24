package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.OrderDetailDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.Order;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.OrderDetail;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // 📌 가맹점 코드 기준으로 발주 신청 내역 조회
    @EntityGraph(attributePaths = {"orderDetails"})
    List<Order> findByFranCode(int franCode);

    // ✅ 네이티브 쿼리로 `OrderDetail + Inventory` 조인 조회
    @Query(value = """
        SELECT od.order_detail_id, od.order_code, od.inven_code, od.order_qty,
               i.inven_code AS inventory_inven_code, i.inven_name, i.expiration_date, i.inven_image
        FROM tbl_order_detail od
        JOIN tbl_inventory i ON od.inven_code = i.inven_code
        WHERE od.order_code IN (:orderCodes)
    """, nativeQuery = true)
    List<Object[]> findOrderDetailsWithInventory(@Param("orderCodes") List<Integer> orderCodes);

}
