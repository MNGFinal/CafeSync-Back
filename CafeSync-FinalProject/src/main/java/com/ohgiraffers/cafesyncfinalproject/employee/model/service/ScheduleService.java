//package com.ohgiraffers.cafesyncfinalproject.employee.model.service;
//
//import com.ohgiraffers.cafesyncfinalproject.employee.model.dao.ScheduleRepository;
//import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.ScheduleDTO;
//import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Schedule;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ScheduleService {
//
//    private final ScheduleRepository scheduleRepository;
//    private final ModelMapper modelMapper;
//
//    public List<ScheduleDTO> getSchedulesByFranCode(int franCode) {
//        List<Schedule> schedules = scheduleRepository.findByFranCode(franCode);
//
//        return schedules.stream()
//                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
//                .collect(Collectors.toList());
//    }
//
//}
