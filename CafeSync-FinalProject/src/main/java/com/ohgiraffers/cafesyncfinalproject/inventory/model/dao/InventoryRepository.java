package com.ohgiraffers.cafesyncfinalproject.inventory.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.entity.Inventory;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByInvenCode(String invenCode);

    @Query("SELECT i FROM Inventory i WHERE i.invenCode = :invenCode AND i.expirationDate = :expirationDate")
    Optional<Inventory> findExistingInventory(
            @Param("invenCode") String invenCode,
            @Param("expirationDate") LocalDate expirationDate
    );

    @Query(value = "SELECT EXISTS(SELECT 1 FROM tbl_inventory WHERE inven_code = :invenCode AND expiration_date = :expirationDate)", nativeQuery = true)
    boolean existsByInvenCodeAndExpirationDate(@Param("invenCode") String invenCode, @Param("expirationDate") java.sql.Timestamp expirationDate);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_inventory (inven_code, expiration_date) VALUES (:invenCode, :expirationDate)", nativeQuery = true)
    void insertNewInventory(@Param("invenCode") String invenCode, @Param("expirationDate") java.sql.Timestamp expirationDate);

}
