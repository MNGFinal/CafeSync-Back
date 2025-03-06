package com.ohgiraffers.cafesyncfinalproject.complain.model.dao;

import com.ohgiraffers.cafesyncfinalproject.complain.model.Entity.Complain;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Integer> {

    @Query(value = "SELECT c.complain_code, c.complain_division, c.complain_date, " +
            "c.customer_phone, c.complain_detail, c.fran_code, " +
            "c.emp_code, e.emp_name " +
            "FROM tbl_complain c " +
            "LEFT JOIN tbl_employee e ON c.emp_code = e.emp_code " +
            "WHERE c.fran_code = :franCode " +
            "AND c.complain_date BETWEEN :startDay AND :endDay " +
            "ORDER BY complain_date desc",
            nativeQuery = true)
    List<Object[]> findComplainDetailsNative(@Param("franCode") int franCode,
                                             @Param("startDay") LocalDateTime startDay,
                                             @Param("endDay") LocalDateTime endDay);


}
