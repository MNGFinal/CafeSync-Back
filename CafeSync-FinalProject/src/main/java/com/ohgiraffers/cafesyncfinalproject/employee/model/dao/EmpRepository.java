package com.ohgiraffers.cafesyncfinalproject.employee.model.dao;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByFranCode(int franCode);

    @Query("SELECT e.empCode, e.empName FROM Employee e WHERE e.empCode IN :empCodes")
    List<Object[]> findEmpNamesByEmpCodes(@Param("empCodes") List<Integer> empCodes);

}
