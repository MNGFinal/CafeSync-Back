package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.FranInvenRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void invenUpdate(List<FranInvenDTO> request) {
        // 1️⃣ 요청에서 PK(franInvenCode) 값만 추출
        List<Integer> franInvenCodes = request.stream()
                .map(FranInvenDTO::getFranInvenCode)
                .collect(Collectors.toList());

        // 2️⃣ DB에서 해당 PK의 엔티티를 한 번에 조회
        List<FranInven> invenList = franInvenRepository.findAllById(franInvenCodes);

        // 3️⃣ 조회된 엔티티를 순회하며 업데이트
        invenList.forEach(inven -> {
            FranInvenDTO dto = request.stream()
                    .filter(r -> r.getFranInvenCode() == inven.getFranInvenCode())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 데이터"));

            // ✅ Setter 대신, 엔티티 내부 메서드 활용
            inven.updateStock(dto.getStockQty(), dto.getOrderQty(), dto.getRecommQty());
        });

        // 4️⃣ Dirty Checking으로 자동 업데이트! (saveAll() 필요 없음)
    }
}
