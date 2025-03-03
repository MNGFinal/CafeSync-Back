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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService; // Firebase Storage 서비스 DI


    // 가맹점별 직원 불러오기
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
}
