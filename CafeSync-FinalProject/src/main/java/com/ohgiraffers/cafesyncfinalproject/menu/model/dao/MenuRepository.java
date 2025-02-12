package com.ohgiraffers.cafesyncfinalproject.menu.model.dao;

import com.ohgiraffers.cafesyncfinalproject.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
