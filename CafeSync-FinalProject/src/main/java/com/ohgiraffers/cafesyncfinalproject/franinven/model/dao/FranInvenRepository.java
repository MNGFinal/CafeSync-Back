package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FranInvenRepository extends JpaRepository<FranInven, Integer> {

    // 로그인된 가맹점의 재고 데이터 가져오기
    List<FranInven> findByFranCode(int franCode);

}
