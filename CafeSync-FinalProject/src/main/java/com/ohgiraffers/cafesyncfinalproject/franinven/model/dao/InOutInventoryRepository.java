package com.ohgiraffers.cafesyncfinalproject.franinven.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.InOutInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InOutInventoryRepository extends JpaRepository<InOutInventory, Integer> {
}
