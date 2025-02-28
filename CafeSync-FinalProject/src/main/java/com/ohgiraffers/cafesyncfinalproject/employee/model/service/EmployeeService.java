package com.ohgiraffers.cafesyncfinalproject.employee.model.service;

import com.ohgiraffers.cafesyncfinalproject.employee.model.dao.EmpRepository;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDTO> findByFranCode(int franCode) {
        List<Employee> workers = empRepository.findByFranCode(franCode);
        return workers.stream()
                .map(worker -> {
                    EmployeeDTO employeeDTO = modelMapper.map(worker, EmployeeDTO.class);
//                    System.out.println("employeeDTO = " + employeeDTO);
                    return employeeDTO;
                })
                .collect(Collectors.toList());
    }

}
