package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.FranInvenRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranInvenService {

    private final FranInvenRepository franInvenRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService; // ✅ Firebase 추가

    // 로그인한 가맹점의 재고조회
    public List<FranInvenDTO> findByFranCode(int franCode) {
        List<FranInven> invenList = franInvenRepository.findByFranCode(franCode);

        return invenList.stream()
                .map(inven -> {
                    FranInvenDTO invenDTO = modelMapper.map(inven, FranInvenDTO.class);
                    // ✅ `invenImage`를 Firebase HTTP URL로 변환
                    if (invenDTO.getInventory() != null && invenDTO.getInventory().getInvenImage() != null) {
                        invenDTO.getInventory().setInvenImage(
                                firebaseStorageService.convertGsUrlToHttp(invenDTO.getInventory().getInvenImage())
                        );
                    }
                    return invenDTO;
                })
                .collect(Collectors.toList());
    }
}
