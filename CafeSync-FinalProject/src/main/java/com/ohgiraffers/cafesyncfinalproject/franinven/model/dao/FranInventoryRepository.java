package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// 입출고 수량 증가 전용 레파지토리
public interface FranInventoryRepository extends JpaRepository<FranInventory, Integer> {

    // ✅ 출고 매장 재고 차감 (출고 수량만큼 감소)
    @Modifying
    @Query("UPDATE FranInventory f SET f.stockQty = f.stockQty - :quantity WHERE f.franCode = :franCode AND f.invenCode = :invenCode AND f.stockQty >= :quantity")
    void decreaseStock(@Param("franCode") int franCode, @Param("invenCode") String invenCode, @Param("quantity") int quantity);

    // ✅ 입고 매장 재고 증가 (입고 수량만큼 증가)
    @Modifying
    @Query("UPDATE FranInventory f SET f.stockQty = f.stockQty + :quantity WHERE f.franCode = :franCode AND f.invenCode = :invenCode")
    void increaseStock(@Param("franCode") int franCode, @Param("invenCode") String invenCode, @Param("quantity") int quantity);

}
