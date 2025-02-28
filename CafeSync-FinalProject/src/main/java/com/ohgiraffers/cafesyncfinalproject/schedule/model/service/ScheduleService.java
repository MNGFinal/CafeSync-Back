package com.ohgiraffers.cafesyncfinalproject.schedule.model.service;

import com.ohgiraffers.cafesyncfinalproject.employee.model.dao.EmpRepository;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.dao.ScheduleRepository;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.entity.Schedule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;

    public List<ScheduleDTO> findByFranCode(int franCode) {
        List<Schedule> schedules = scheduleRepository.findByFranCode(franCode);

        return schedules.stream()
                .map(schedule -> {
                    ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
                    scheduleDTO.setEmpName(schedule.getEmployee().getEmpName());
                    return scheduleDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleDTO> saveSchedule(List<ScheduleDTO> scheduleList) {
        List<Schedule> schedules = scheduleList.stream()
                .map(dto -> modelMapper.map(dto, Schedule.class))
                .collect(Collectors.toList());

        List<Schedule> savedSchedules = scheduleRepository.saveAll(schedules);

        // 저장된 스케줄을 다시 조회해서 employee 정보 포함사키기
        List<Integer> empCodes = savedSchedules.stream()
                .map(Schedule::getEmpCode)
                .distinct() // 중복 제거
                .collect(Collectors.toList());
        List<Object[]> empResults = empRepository.findEmpNamesByEmpCodes(empCodes);
        Map<Integer, String> empNameMap = empResults.stream()
                .collect(Collectors.toMap(
                        result -> (Integer) result[0],  // empCode
                        result -> (String) result[1]    // empName
                ));

        return savedSchedules.stream()
                .map(schedule -> {
                    ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);
                    scheduleDTO.setEmpName(empNameMap.get(schedule.getEmpCode()));
                    return scheduleDTO;
                })
                .collect(Collectors.toList());
    }

    public ScheduleDTO findByScheduleCode(int scheduleCode) {
        Schedule findSchedule = scheduleRepository.findByScheduleCode(scheduleCode);

        if (findSchedule != null) {
            ScheduleDTO scheduleDTO = modelMapper.map(findSchedule, ScheduleDTO.class);
            scheduleDTO.setEmpName(findSchedule.getEmployee().getEmpName());
            return scheduleDTO;
        } else {
            return null;
        }
    }

    @Transactional
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        ScheduleDTO savedScheduleDTO = modelMapper.map(savedSchedule, ScheduleDTO.class);

        return savedScheduleDTO;
    }

    public void deleteSchedule(int scheduleCode) {
        scheduleRepository.deleteById(scheduleCode);
    }

}
