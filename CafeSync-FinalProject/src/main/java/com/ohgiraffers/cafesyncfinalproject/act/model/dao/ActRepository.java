package com.ohgiraffers.cafesyncfinalproject.act.model.dao;

import com.ohgiraffers.cafesyncfinalproject.act.model.entity.Act;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActRepository extends JpaRepository<Act, Integer> {
}
