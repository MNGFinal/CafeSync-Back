package com.ohgiraffers.cafesyncfinalproject.complain.model.service;

import com.ohgiraffers.cafesyncfinalproject.complain.model.Entity.Complain;
import com.ohgiraffers.cafesyncfinalproject.complain.model.dao.ComplainRepository;
import com.ohgiraffers.cafesyncfinalproject.complain.model.dto.ComplainDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dao.EmpRepository;
import com.ohgiraffers.cafesyncfinalproject.schedule.model.dao.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplainService {

    private final ComplainRepository complainRepository;
    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;

    public List<ComplainDTO> findByFranCodeAndDateRange(int franCode, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDay = startDate.atStartOfDay();
        LocalDateTime endDay = endDate.atTime(23, 59, 59);
        List<Object[]> complains = complainRepository.findComplainDetailsNative(franCode, startDay, endDay);

        return complains.stream()
                .map(row -> new ComplainDTO(
                        (int) row[0],  // complainCode
                        (int) row[1],  // complainDivision
                        ((Timestamp) row[2]).toLocalDateTime(),  // complainDate
                        (String) row[3],  // customerPhone
                        (String) row[4],  // complainDetail
                        (int) row[5],  // franCode
                        (int) row[6],  // empCode
                        (String) row[7]  // empName (조인된 값)
                ))
                .collect(Collectors.toList());
    }

    public ComplainDTO saveComplain(ComplainDTO complain) {
        Complain addComplain = modelMapper.map(complain, Complain.class);

        Complain savedComplain = complainRepository.save(addComplain);
        return modelMapper.map(savedComplain, ComplainDTO.class);
    }
}
