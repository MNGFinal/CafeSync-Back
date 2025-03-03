package com.ohgiraffers.cafesyncfinalproject.employee.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 직원 전체 조회
    @Operation(summary = "전체 직원 목록 조회", description = "전체 직원 목록을 조회합니다.")
    @GetMapping("/employees")
    public ResponseEntity<ResponseDTO> getEmployeeList() {
        try {
            List<EmployeeDTO> employeeList = employeeService.findAllEmployees();

            if (employeeList == null || employeeList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "직원 목록을 찾을 수 없습니다.", null));
            }

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "전체 직원 목록이 성공적으로 조회되었습니다.", employeeList));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.", null));
        }
    }

}
