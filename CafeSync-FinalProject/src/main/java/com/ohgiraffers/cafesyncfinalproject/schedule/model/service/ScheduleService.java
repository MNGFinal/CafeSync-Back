package com.ohgiraffers.cafesyncfinalproject.schedule.model.service;

import com.ohgiraffers.cafesyncfinalproject.schedule.model.dao.ScheduleRepository;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    // 스케줄 전체 조회 - 가맹점별
    public List<ScheduleDTO> findByFranCode(int franCode) {
        System.out.println("스케줄 서비스 franCode = " + franCode);
        List<Schedule> schedules = scheduleRepository.findByFranCode(franCode);
        System.out.println("스케줄 서비스 단의 schedules = " + schedules);

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    // 스케줄 구분별 조회 - 가맹점&날짜별
//    public List<ScheduleDTO> findSchedulesWithDivisionsByFranCode(int franCode) {
//        System.out.println("스케줄 날짜&가맹별 조회시 서비스단 franCode = " + franCode);
//        List<ScheduleDTO> scheduleDTOs = scheduleRepository.findSchedulesWithDivisionByFranCode(franCode);
//        System.out.println("스케줄 날짜&가맹별 조회시 서비스단 scheduleDTOs = " + scheduleDTOs);
//
//        return scheduleDTOs;
//    }

}
