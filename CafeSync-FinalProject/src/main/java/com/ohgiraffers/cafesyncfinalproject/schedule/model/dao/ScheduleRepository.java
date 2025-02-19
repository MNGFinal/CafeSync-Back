package com.ohgiraffers.cafesyncfinalproject.schedule.model.dao;

import com.ohgiraffers.cafesyncfinalproject.schedule.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // 스케줄 조회 - 가맹점 코드별로 상이하게
//    @Query(
//            value = "SELECT a.*, b.EMP_NAME " +
//                    "FROM TBL_SCHEDULE a " +
//                    "JOIN TBL_EMPLOYEE b " +
//                    "ON a.EMP_CODE = b.EMP_CODE " +
//                    "WHERE a.FRAN_CODE = :franCode",
//            nativeQuery = true)
    List<Schedule> findByFranCode(@Param("franCode") int franCode);
//    List<Schedule> findByFranCode(int franCode);

}
