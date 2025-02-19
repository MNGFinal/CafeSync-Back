package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.InOutInventoryRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.InOutRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.*;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.InOut;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.InOutInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InOutService {

    private final InOutRepository inOutRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // ✅ JSON 파서 추가
    private final InOutInventoryRepository inOutInventoryRepository;

    // ✅ 입출고 내역 조회 (재고 포함)
    public List<InOutDTO> getInOutList(int franCode) {
        List<Object[]> results = inOutRepository.findInOutWithInventory(franCode);

        List<InOutDTO> inOutList = new ArrayList<>();

        for (Object[] row : results) {
            int inoutCode = (Integer) row[0];

            List<InOutInventoryDTO> inventoryList = new ArrayList<>();
            try {
                // ✅ JSON 파싱해서 inventoryList 변환
                String jsonInventoryList = (String) row[10];
                InOutInventoryDTO[] parsedInventory = objectMapper.readValue(jsonInventoryList, InOutInventoryDTO[].class);
                inventoryList = Arrays.asList(parsedInventory);
            } catch (Exception e) {
                System.out.println("❌ JSON 파싱 오류: " + e.getMessage());
            }

            InOutDTO inOutDTO = new InOutDTO(
                    inoutCode,
                    (Integer) row[1], // inoutDivision
                    convertToLocalDateTime(row[2]), // inoutDate 변환
                    (Integer) row[3], // inoutStatus
                    new FranchiseDTO( // 출고 가맹점
                            (Integer) row[4],
                            (String) row[5],
                            (String) row[6]
                    ),
                    new FranchiseDTO( // 입고 가맹점
                            (Integer) row[7],
                            (String) row[8],
                            (String) row[9]
                    ),
                    inventoryList // ✅ 여러 개의 재고 포함
            );

            inOutList.add(inOutDTO);
        }

        return inOutList;
    }

    private LocalDateTime convertToLocalDateTime(Object timestampObj) {
        if (timestampObj instanceof Timestamp) {
            return ((Timestamp) timestampObj).toLocalDateTime();
        } else if (timestampObj instanceof String) {
            return LocalDateTime.parse((String) timestampObj);
        } else {
            return null;
        }
    }

    // ✅ 출고 등록 메서드 (엔티티 변환 적용)
    @Transactional
    public boolean registerOut(List<InOutInventoryJoinDTO> request) {
        try {
            for (InOutInventoryJoinDTO outData : request) {
                // ✅ 출고 매장 및 입고 매장 코드 (null 체크 추가)
                int franOutCode = (outData.getFranOutCode() != null) ? outData.getFranOutCode().getFranCode() : 0;
                int franInCode = (outData.getFranInCode() != null) ? outData.getFranInCode().getFranCode() : 0;

                // 1️⃣ `tbl_inout` 테이블에 INSERT
                InOut newOut = new InOut(2, LocalDateTime.now(), 0, franOutCode, franInCode);

                // 저장 후 생성된 inout_code 가져오기
                InOut savedOut = inOutRepository.save(newOut);
                int lastInoutCode = savedOut.getInoutCode();

                // 2️⃣ `tbl_inout_inventory`에 출고 상품 데이터 INSERT (null 체크 추가)
                List<InOutInventory> inventoryList = (outData.getInventoryList() != null) ? outData.getInventoryList().stream()
                        .map(inventory -> new InOutInventory(
                                lastInoutCode,
                                (inventory.getInvenCode() != null) ? inventory.getInvenCode() : "", // invenCode null 체크
                                (inventory.getQuantity() != null) ? inventory.getQuantity() : 0 // quantity null 체크
                        ))
                        .collect(Collectors.toList()) : Collections.emptyList();

                inOutInventoryRepository.saveAll(inventoryList);
            }
            return true; // 성공
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 실패
        }
    }
}
