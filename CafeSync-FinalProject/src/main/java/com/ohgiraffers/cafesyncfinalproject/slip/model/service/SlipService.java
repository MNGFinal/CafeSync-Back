package com.ohgiraffers.cafesyncfinalproject.slip.model.service;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.SlipRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.ActDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlipService {

    private final SlipRepository slipRepository;

    // 가맹점 전표 조회
    public List<SlipDTO> findFranSlips(int franCode, String startDate, String endDate) {
        List<Object[]> resultList = slipRepository.findFranSlips(franCode, startDate, endDate);

        return resultList.stream().map(obj -> new SlipDTO(
                (int) obj[0],                                    // slipCode
                ((Timestamp) obj[1]).toLocalDateTime(),          // ✅ Timestamp → LocalDateTime 변환
                new VendorDTO((int) obj[2], (String) obj[3]),    // venCode
                (String) obj[4],                                 // slipDivision
                new ActDTO((int) obj[5], (String) obj[6], (String) obj[7]), // actCode
                new SummaryDTO((String) obj[8], (String) obj[9]), // summaryCode
                (Integer) obj[10],                               // debit
                (Integer) obj[11],                               // credit
                (int) obj[12]                                    // franCode
        )).collect(Collectors.toList());
    }
}
