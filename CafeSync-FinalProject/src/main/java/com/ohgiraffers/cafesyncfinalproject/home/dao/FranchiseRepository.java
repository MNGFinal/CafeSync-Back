package com.ohgiraffers.cafesyncfinalproject.home.dao;

import com.ohgiraffers.cafesyncfinalproject.home.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {
}
