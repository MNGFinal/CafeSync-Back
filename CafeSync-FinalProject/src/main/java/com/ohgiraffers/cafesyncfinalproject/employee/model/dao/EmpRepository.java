package com.ohgiraffers.cafesyncfinalproject.employee.model.dao;

import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {

    @Query(value = """
            SELECT e.emp_code, e.emp_name, e.profile_image, e.addr, e.phone, e.email, 
                   e.hire_date, e.retire_date, e.memo, e.job_code, e.salary_unit, 
                   e.salary, e.fran_code, j.job_name
            FROM tbl_employee e
            JOIN tbl_job j ON e.job_code = j.job_code
            WHERE e.fran_code = :franCode
            """, nativeQuery = true)
    List<Object[]> findByFranCodeWithJob(@Param("franCode") int franCode);

    @Query("SELECT e.empCode, e.empName FROM Employee e WHERE e.empCode IN :empCodes")
    List<Object[]> findEmpNamesByEmpCodes(@Param("empCodes") List<Integer> empCodes);


    @Query(value = "SELECT e.emp_code, e.emp_name, e.profile_image, e.addr, e.phone, e.email, e.hire_date, e.retire_date, e.memo, " +
            "e.job_code, e.salary_unit, e.salary, e.fran_code, j.job_name, f.fran_name " +
            "FROM tbl_employee e " +
            "LEFT JOIN tbl_job j ON e.job_code = j.job_code " +
            "LEFT JOIN tbl_franchise f ON e.fran_code = f.fran_code", nativeQuery = true)
    List<Object[]> findAllEmployeeWithJoins();


    String findNameByEmpCode(Integer empCode);

    // 중복 이메일 검증
    boolean existsByEmail(String email);
}
