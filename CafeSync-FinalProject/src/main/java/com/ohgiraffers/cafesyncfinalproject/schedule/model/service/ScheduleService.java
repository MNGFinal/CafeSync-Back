package com.ohgiraffers.cafesyncfinalproject.schedule.model.service;

import com.ohgiraffers.cafesyncfinalproject.schedule.model.dao.ScheduleRepository;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.entity.Schedule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
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
                    scheduleDTO.setEmpName(schedule.getEmployee().getEmpName());
                    System.out.println("scheduleDTO = " + scheduleDTO);
                    return scheduleDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveSchedule(List<ScheduleDTO> scheduleList) {
        List<Schedule> schedules = scheduleList.stream()
                .map(dto -> {
                    Schedule schedule = modelMapper.map(dto, Schedule.class);
                    return Schedule.builder()
                            .scheduleCode(schedule.getScheduleCode()) // 기존 값 유지
                            .scheduleDate(setScheduleTime(dto.getScheduleDate(), dto.getScheduleDivision())) // ✅ 시간 추가된 날짜 설정
                            .empCode(schedule.getEmpCode()) // 기존 값 유지
                            .scheduleDivision(schedule.getScheduleDivision()) // 기존 값 유지
                            .franCode(schedule.getFranCode()) // 기존 값 유지
                            .build();
                })
                .collect(Collectors.toList());

        scheduleRepository.saveAll(schedules);
    }

    private Date setScheduleTime(Date date, int scheduleDivision) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalTime scheduleTime;
        switch (scheduleDivision) {
            case 1: scheduleTime = LocalTime.of(9, 0); break;
            case 2: scheduleTime = LocalTime.of(13, 0); break;
            case 3: scheduleTime = LocalTime.of(17, 0); break;
            case 4: scheduleTime = LocalTime.of(0, 0); break;
            default: scheduleTime = LocalTime.of(0, 0);
        }

        LocalDateTime scheduleDateTime = LocalDateTime.of(localDate, scheduleTime);

        return Date.from(scheduleDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
