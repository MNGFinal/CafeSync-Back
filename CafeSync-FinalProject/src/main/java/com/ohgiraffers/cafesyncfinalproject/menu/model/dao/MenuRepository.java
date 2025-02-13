package com.ohgiraffers.cafesyncfinalproject.menu.model.dao;

import com.ohgiraffers.cafesyncfinalproject.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByCategoryCode(int categoryCode);
}
