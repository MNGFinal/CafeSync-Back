package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.InOutRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InOutService {

    private final InOutRepository inOutRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // ✅ JSON 파서 추가

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
}
