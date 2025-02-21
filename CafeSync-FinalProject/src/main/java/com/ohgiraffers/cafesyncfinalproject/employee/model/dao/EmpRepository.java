package com.ohgiraffers.cafesyncfinalproject.employee.model.dao;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFranCode(int franCode);
}
