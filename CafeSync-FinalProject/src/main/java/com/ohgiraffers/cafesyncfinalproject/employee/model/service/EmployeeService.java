package com.ohgiraffers.cafesyncfinalproject.employee.model.service;

import com.ohgiraffers.cafesyncfinalproject.employee.model.dao.EmpRepository;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.FranChiseJoinDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dto.JobDTO;
import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService; // Firebase Storage 서비스 DI


    // ✅ 가맹점별 직원 조회 (직급 포함)
    public List<EmployeeDTO> findByFranCode(int franCode) {
        List<Object[]> workers = empRepository.findByFranCodeWithJob(franCode);
        return workers.stream().map(record -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmpCode((Integer) record[0]);
            dto.setEmpName((String) record[1]);
            dto.setProfileImage((String) record[2]);

            // ✅ 프로필 이미지가 있으면 Firebase URL 변환
            if (dto.getProfileImage() != null) {
                dto.setProfileImage(firebaseStorageService.convertGsUrlToHttp(dto.getProfileImage()));
            }

            dto.setAddr((String) record[3]);
            dto.setPhone((String) record[4]);
            dto.setEmail((String) record[5]);
            dto.setHireDate((Date) record[6]);
            dto.setRetireDate((Date) record[7]);
            dto.setMemo((String) record[8]);
            dto.setJobCode((Integer) record[9]);
            dto.setSalaryUnit((Integer) record[10]);
            dto.setSalary((Integer) record[11]);
            dto.setFranCode((Integer) record[12]);

            // ✅ 직급 정보 추가
            JobDTO jobDto = new JobDTO();
            jobDto.setJobName((String) record[13]);
            dto.setJob(jobDto);

            return dto;
        }).collect(Collectors.toList());
    }


    // 전체 직원 불러오기 (조인 정보 포함)
    public List<EmployeeDTO> findAllEmployees() {
        List<Object[]> results = empRepository.findAllEmployeeWithJoins();
        return results.stream().map(record -> {

            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmpCode((Integer) record[0]);
            dto.setEmpName((String) record[1]);
            dto.setProfileImage((String) record[2]);
            // 프로필 사진 URL 변환
            if (dto.getProfileImage() != null) {
                dto.setProfileImage(firebaseStorageService.convertGsUrlToHttp(dto.getProfileImage()));
            }
            dto.setAddr((String) record[3]);
            dto.setPhone((String) record[4]);
            dto.setEmail((String) record[5]);
            dto.setHireDate((Date) record[6]);
            dto.setRetireDate((Date) record[7]);
            dto.setMemo((String) record[8]);
            dto.setJobCode((Integer) record[9]);
            dto.setSalaryUnit((Integer) record[10]);
            dto.setSalary((Integer) record[11]);
            dto.setFranCode((Integer) record[12]);

            // JobDTO 매핑 (예시: job_name)
            JobDTO jobDto = new JobDTO();
            jobDto.setJobName((String) record[13]);
            dto.setJob(jobDto);

            // FranChiseJoinDTO 매핑 (예시: fran_name)
            FranChiseJoinDTO franDto = new FranChiseJoinDTO();
            franDto.setFranName((String) record[14]);
            dto.setFranChise(franDto);

            return dto;
        }).collect(Collectors.toList());
    }

    // 직원 업데이트
    @Transactional
    public boolean updateEmployee(EmployeeDTO employeeDTO) {
        // 1. 직원 엔티티 조회
        Employee existingEmployee = empRepository.findById(employeeDTO.getEmpCode())
                .orElse(null);

        if (existingEmployee == null) {
            return false; // 직원이 존재하지 않으면 false 반환
        }

        // 2. ModelMapper를 사용하여 DTO → Entity 변환 (부분 업데이트)
        Employee updatedEmployee = modelMapper.map(employeeDTO, Employee.class);

        // 3. 기존 엔티티의 ID 유지 (JPA에서 새로운 데이터로 인식하지 않도록)
        updatedEmployee = new Employee(
                existingEmployee.getEmpCode(), // ID 유지
                updatedEmployee.getEmpName(),
                updatedEmployee.getProfileImage() != null ?
                        firebaseStorageService.convertGsUrlToHttp(updatedEmployee.getProfileImage())
                        : existingEmployee.getProfileImage(), // 프로필 이미지 처리
                updatedEmployee.getAddr(),
                updatedEmployee.getPhone(),
                updatedEmployee.getEmail(),
                updatedEmployee.getHireDate(),
                updatedEmployee.getRetireDate(),
                updatedEmployee.getMemo(),
                updatedEmployee.getJobCode(),
                updatedEmployee.getSalaryUnit(),
                updatedEmployee.getSalary(),
                existingEmployee.getFranCode() // 가맹점 코드 유지
        );

        // 4. 변경된 정보 저장
        empRepository.save(updatedEmployee);

        return true; // 성공적으로 업데이트되었으면 true 반환
    }

    // ✅ 직원 등록
    @Transactional
    public boolean createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getEmpName() == null || employeeDTO.getPhone() == null || employeeDTO.getEmail() == null) {
            return false; // 필수 정보 누락 시 실패
        }

        if (empRepository.existsByEmail(employeeDTO.getEmail())) {
            return false; // 중복 이메일 방지
        }

        // DTO → Entity 변환 (Builder 패턴 적용)
        Employee newEmployee = Employee.builder()
                .empName(employeeDTO.getEmpName())
                .phone(employeeDTO.getPhone())
                .email(employeeDTO.getEmail())
                .hireDate(employeeDTO.getHireDate())
                .retireDate(employeeDTO.getRetireDate())
                .salaryUnit(employeeDTO.getSalaryUnit())
                .salary(employeeDTO.getSalary())
                .addr(employeeDTO.getAddr())
                .memo(employeeDTO.getMemo())
                .jobCode(employeeDTO.getJobCode())
                .profileImage(employeeDTO.getProfileImage() != null ?
                        firebaseStorageService.convertGsUrlToHttp(employeeDTO.getProfileImage())
                        : null) // 프로필 이미지 URL 변환
                .franCode(employeeDTO.getFranCode()) // ✅ 가맹점 코드 추가
                .build();

        empRepository.save(newEmployee);
        return true;
    }
}
