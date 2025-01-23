package com.ohgiraffers.cafesyncfinalproject.inventory.model.dao;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
}
