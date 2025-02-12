package com.ohgiraffers.cafesyncfinalproject.account.model.dao;

import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String username);

    @Query(value = """
    SELECT\s
        u.user_id AS userId,\s
        u.email AS email,\s
        u.authority AS authority,\s
       \s
        e.emp_code AS empCode, e.emp_name AS empName, e.phone AS empPhone, e.email AS empEmail,\s
       \s
        j.job_code AS jobCode, j.job_name AS jobName,\s
       \s
        f.fran_code AS franCode, f.fran_name AS franName, f.fran_addr AS franAddr\s
    FROM tbl_account u
    INNER JOIN tbl_employee e ON u.emp_code = e.emp_code
    INNER JOIN tbl_job j ON u.job_code = j.job_code
    INNER JOIN tbl_franchise f ON u.store_code = f.fran_code
    WHERE u.user_id = :userId;
""", nativeQuery = true)
    List<Object[]> findUserLoginDetails(@Param("userId") String userId);

}
