package com.ohgiraffers.cafesyncfinalproject.account.model.service;

import com.ohgiraffers.cafesyncfinalproject.account.model.dao.UserRepository;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.EmployeeDTO;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.FranchiseDTO;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.JobDTO;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.UserDTO;
import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import com.ohgiraffers.cafesyncfinalproject.account.model.dto.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // ✅ 회원가입 (DTO → Entity 변환 후 저장)
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class); // DTO → Entity 변환
        user.setUserPass(passwordEncoder.encode(user.getUserPass())); // 비밀번호 암호화

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class); // 저장 후 DTO로 변환하여 반환
    }

    // 로그인 정보 가져오기
    public User findUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public UserLoginDTO findUserLoginDetails(String userId) {

        List<Object[]> result = userRepository.findUserLoginDetails(userId);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
        }

        Object[] row = result.get(0);
        System.out.println("🔹 로그인 데이터 확인: " + Arrays.deepToString(row));

        // ✅ Employee 객체 생성
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpCode(row[3] != null ? ((Number) row[3]).intValue() : 0);
        employeeDTO.setEmpName((String) row[4]);
        employeeDTO.setPhone((String) row[5]);
        employeeDTO.setEmail((String) row[6]);

        // ✅ Job 객체 생성
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobCode(row[7] != null ? ((Number) row[7]).intValue() : 0);
        jobDTO.setJobName((String) row[8]);

        // ✅ Franchise 객체 생성
        FranchiseDTO franchiseDTO = new FranchiseDTO();
        franchiseDTO.setFranCode(row[9] != null ? ((Number) row[9]).intValue() : 0);
        franchiseDTO.setFranName((String) row[10]);
        franchiseDTO.setFranAddr((String) row[11]);

        // ✅ UserLoginDTO 객체 생성 및 데이터 설정
        return new UserLoginDTO(
                (String) row[0], // userId
                (String) row[1], // email
                row[2] != null ? ((Number) row[2]).intValue() : 0, // authority
                employeeDTO, // ✅ Employee 객체 포함
                jobDTO,      // ✅ Job 객체 포함
                franchiseDTO // ✅ Franchise 객체 포함
        );
    }

}
