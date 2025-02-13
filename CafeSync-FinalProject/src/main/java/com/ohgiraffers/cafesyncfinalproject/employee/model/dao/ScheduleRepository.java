//package com.ohgiraffers.cafesyncfinalproject.employee.model.dao;
//
//import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.ScheduleDTO;
//import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Schedule;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
//
//    // 스케줄 조회 - 가맹점 코드별로 상이하게
////    @Query("SELECT s FROM Schedule s WHERE s.franCode = :franCode")
////    List<Schedule> findByFranCode(@Param("franCode") int franCode);
//    List<Schedule> findByFranCode(int franCode);
//
//}
