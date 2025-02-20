package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
