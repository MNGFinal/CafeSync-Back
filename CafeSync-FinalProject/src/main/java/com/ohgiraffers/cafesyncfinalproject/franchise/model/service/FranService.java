package com.ohgiraffers.cafesyncfinalproject.franchise.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dao.FranRepository;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.Fran;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dto.FranDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranService {

    private final FranRepository franRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService;

    // ✅ 가맹점 전체 조회
    public List<FranDTO> findAllFran() {
        return convertToFranDTOList(franRepository.findAll());
    }
    // ✅ 가맹점 검색
    public List<FranDTO> findFransByQuery(String query) {
        return convertToFranDTOList(franRepository.findByFranNameContaining(query));
    }

    // ✅ DTO 변환 공통 메서드
    private List<FranDTO> convertToFranDTOList(List<Fran> franList) {
        return franList.stream()
                .map(fran -> {
                    FranDTO franDTO = modelMapper.map(fran, FranDTO.class);

                    if (fran.getEmployee() != null) {
                        franDTO.setEmpCode(fran.getEmployee().getEmpCode());
                        franDTO.setEmpName(fran.getEmployee().getEmpName());
                    }

                    if (franDTO.getFranImage() != null) {
                        franDTO.setFranImage(firebaseStorageService.convertGsUrlToHttp(franDTO.getFranImage()));
                    }

                    return franDTO;
                })
                .collect(Collectors.toList());
    }

    // ✅ 가맹점 등록
    @Transactional
    public FranDTO registFran(FranDTO franDTO) {

        // ModelMapper를 이용해 DTO -> Entity 변환
        Fran fran = modelMapper.map(franDTO, Fran.class);

        // DB에 저장
        Fran savedFran = franRepository.save(fran);

        // 저장된 Entity를 DTO로 변환 후 반환
        return modelMapper.map(savedFran, FranDTO.class);
    }

    // ✅ 가맹점 삭제
    @Transactional
    public void deleteFran(int franCode) {
        try {
            Fran fran = franRepository.getReferenceById(franCode);
            franRepository.delete(fran);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("이미 삭제되었거나 존재하지 않는 가맹점입니다.");
        }
    }

    // ✅ 가맹점 수정
    @Transactional
    public FranDTO modifyFran(FranDTO franDTO) {

        // 1️⃣ 기존 가맹점 찾기
        Fran foundFran = franRepository.findById(franDTO.getFranCode())
                .orElseThrow(IllegalArgumentException::new);

        System.out.println("DTO 에서 찾은 Entity 값 = " + foundFran);

        // 2️⃣ 학원 스타일의 Builder 패턴 적용하여 값 변경
        foundFran = foundFran.franName(franDTO.getFranName())
                  .franPhone(franDTO.getFranPhone())
                  .memo(franDTO.getMemo())
                  .empCode(franDTO.getEmpCode())
                  .builder();

        // 3️⃣ save() 호출 → JPA가 ID를 보고 UPDATE 수행
        Fran updatedFran = franRepository.save(foundFran);

        // 4️⃣ DTO 변환 후 반환
        return modelMapper.map(updatedFran, FranDTO.class);
    }


}


