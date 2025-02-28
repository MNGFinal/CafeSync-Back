package com.ohgiraffers.cafesyncfinalproject.complain.model.dao;

import com.ohgiraffers.cafesyncfinalproject.complain.model.Entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Integer> {

    List<Complain> findByFranCode(int franCode);

}
