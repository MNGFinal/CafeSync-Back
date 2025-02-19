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

    public List<ScheduleDTO> findByFranCode(int franCode) {
        System.out.println("스케줄 서비스 franCode = " + franCode);
        List<Schedule> schedules = scheduleRepository.findByFranCode(franCode);
        System.out.println("스케줄 서비스 단의 schedules = " + schedules);

        return schedules.stream()
                .map(schedule -> {
                    ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
//                    scheduleDTO.setEmpName(schedule.getEmployee().getEmpName());
//                    System.out.println("scheduleDTO = " + scheduleDTO);
                    return scheduleDTO;
                })
                .collect(Collectors.toList());
    }

}
