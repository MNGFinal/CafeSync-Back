package com.ohgiraffers.cafesyncfinalproject.schedule.model.dao;

import com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // 스케줄 전체 조회 - 가맹점 코드별로 상이하게
    List<Schedule> findByFranCode(int franCode);

    // 스케줄 구분별 유무 조회 - 가맹점 코드별로 상이하게
//    @Query("""
//        SELECT new com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO(
//            s.scheduleDate,
//            CASE WHEN s.scheduleDivision = 1 THEN true ELSE false END,
//            CASE WHEN s.scheduleDivision = 2 THEN true ELSE false END,
//            CASE WHEN s.scheduleDivision = 3 THEN true ELSE false END,
//            CASE WHEN s.scheduleDivision = 4 THEN true ELSE false END
//            )
//        FROM Schedule s
//        WHERE s.franCode = :franCode
//    """)
//    List<ScheduleDTO> findSchedulesWithDivisionByFranCode(@Param("franCode") int franCode);




}
