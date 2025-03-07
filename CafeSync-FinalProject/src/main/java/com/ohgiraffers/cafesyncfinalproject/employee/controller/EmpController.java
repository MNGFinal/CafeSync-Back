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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "EmpController", description = "가맹점 - 직원 관리")
public class EmpController {

    private final EmployeeService employeeService;

    // 가맹점별 직원 조회
    @GetMapping("/employee/workers/{franCode}")
    public List<EmployeeDTO> findWorkersByFranCode(@PathVariable int franCode) {
        List<EmployeeDTO> workers = employeeService.findByFranCode(franCode);

        System.out.println("workers = " + workers);

        return workers;
    }

    // 직원 전체 조회
    @Operation(summary = "전체 직원 목록 조회", description = "전체 직원 목록을 조회합니다.")
    @GetMapping("/employees")
    public ResponseEntity<ResponseDTO> getEmployeeList() {
        try {
            System.out.println("📢 직원 목록 조회 API 호출됨!");
            List<EmployeeDTO> employeeList = employeeService.findAllEmployees();
            System.out.println("📢 컨트롤러에서 받은 employeeList: " + employeeList);
            System.out.println("📢 컨트롤러에서 받은 employeeList 개수: " + employeeList.size());

            if (employeeList == null || employeeList.isEmpty()) {
                System.out.println("❌ 직원 목록이 비어 있음");
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "직원 목록을 찾을 수 없습니다.", new ArrayList<>()));
            }

            ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "전체 직원 목록이 성공적으로 조회되었습니다.", employeeList);
            System.out.println("📢 ResponseDTO 생성됨: " + responseDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.", null));
        }
    }


    // 직원 정보 업데이트
    @Operation(summary = "직원 정보 업데이트", description = "주어진 직원 정보를 업데이트합니다.")
    @PutMapping("/employee")
    public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            boolean isUpdated = employeeService.updateEmployee(employeeDTO);

            if (!isUpdated) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "직원 정보 업데이트 실패", null));
            }

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "직원 정보가 성공적으로 업데이트되었습니다.", employeeDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.", null));
        }
    }

    // ✅ 직원 등록 (POST)
    @Operation(summary = "직원 등록", description = "신규 직원을 등록합니다.")
    @PostMapping("/employee")
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            boolean isCreated = employeeService.createEmployee(employeeDTO);

            if (!isCreated) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "직원 등록 실패", null));
            }

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "직원 등록 완료", employeeDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }
}
