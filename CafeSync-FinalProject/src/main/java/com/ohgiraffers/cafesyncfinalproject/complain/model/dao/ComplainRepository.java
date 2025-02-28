package com.ohgiraffers.cafesyncfinalproject.complain.model.dao;

import com.ohgiraffers.cafesyncfinalproject.complain.model.Entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Integer> {

    List<Complain> findByFranCodeAndComplainDateBetween(int franCode, LocalDateTime startDay, LocalDateTime endDay);

}
