package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.InOutInventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InOutInventoryRepository extends JpaRepository<InOutInventory, Integer> {

    // 특정 inoutCode에 해당하는 invenCode와 quantity를 가져오기
    @Query("SELECT ii FROM InOutInventory ii WHERE ii.inoutCode = :inoutCode")
    List<InOutInventory> findByInoutCode(@Param("inoutCode") int inoutCode);
}
