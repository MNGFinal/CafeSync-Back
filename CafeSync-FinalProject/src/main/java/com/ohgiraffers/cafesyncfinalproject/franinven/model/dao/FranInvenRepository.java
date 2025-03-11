package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FranInvenRepository extends JpaRepository<FranInven, Integer> {

    // 로그인된 가맹점의 재고 데이터 가져오기
    List<FranInven> findByFranCode(int franCode);

    @Query(value = "SELECT stock_qty FROM tbl_franinven WHERE fran_code = :franCode AND inven_code = :invenCode AND last_in = :expirationDate", nativeQuery = true)
    Optional<Integer> findExistingStock(@Param("franCode") int franCode, @Param("invenCode") String invenCode, @Param("expirationDate") java.sql.Timestamp expirationDate);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_franinven SET stock_qty = stock_qty + :orderQty WHERE fran_code = :franCode AND inven_code = :invenCode AND last_in = :expirationDate", nativeQuery = true)
    void updateStockQty(@Param("franCode") int franCode, @Param("invenCode") String invenCode, @Param("expirationDate") java.sql.Timestamp expirationDate, @Param("orderQty") int orderQty);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_franinven (fran_code, inven_code, last_in, stock_qty) VALUES (:franCode, :invenCode, :expirationDate, :stockQty)", nativeQuery = true)
    void insertNewFranInven(@Param("franCode") int franCode, @Param("invenCode") String invenCode, @Param("expirationDate") java.sql.Timestamp expirationDate, @Param("stockQty") int stockQty);
}
