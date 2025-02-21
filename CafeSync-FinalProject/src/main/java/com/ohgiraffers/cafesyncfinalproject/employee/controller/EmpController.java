package com.ohgiraffers.cafesyncfinalproject.employee.controller;

import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "EmpController", description = "가맹점 - 직원 관리")
public class EmpController {

    private final EmployeeService employeeService;

    // 아직 진행 안하는 중
    @GetMapping("/employee/workers/{franCode}")
    public List<EmployeeDTO> findWorkersByFranCode(@PathVariable int franCode) {
        List<EmployeeDTO> workers = employeeService.findByFranCode(franCode);
        return workers;
    }

}
