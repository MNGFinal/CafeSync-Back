package com.ohgiraffers.cafesyncfinalproject.menu.model.dao;

import com.ohgiraffers.cafesyncfinalproject.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByCategoryCode(int categoryCode);

    @Query(
        value="select m from Menu m where m.categoryCode =:categoryCode and m.menuNameKo like %:query%"
    )
    List<Menu> findByCategoryCodeAndMenuName(int categoryCode, String query);

    @Query(
//   여기에 쿼리문 작성
    )
    Menu findByMenuCode(int menuCode);
}
