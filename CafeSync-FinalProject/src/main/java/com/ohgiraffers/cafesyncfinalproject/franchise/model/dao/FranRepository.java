package com.ohgiraffers.cafesyncfinalproject.franchise.model.dao;

import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.Fran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FranRepository extends JpaRepository<Fran, Integer> {

    @Query("SELECT f FROM Fran f JOIN FETCH f.employee")
    List<Fran> findAllWithEmployee();
}
