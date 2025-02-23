package com.ohgiraffers.cafesyncfinalproject.slip.model.service;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.SlipRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.ActDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Slip;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    // 전표 추가 및 수정
    @Transactional
    public void saveSlipList(List<SlipInsertDTO> slipInsertList) {
        for (SlipInsertDTO dto : slipInsertList) {
            if (dto.getSlipCode() == 0 || !slipRepository.existsById(dto.getSlipCode())) {
                // slipCode가 0이거나, DB에 없는 경우에만 INSERT
                insertSlip(dto);
            } else {
                // slipCode가 0이 아니고, 기존에 존재하면 UPDATE
                updateSlip(dto);
            }
        }
    }


    // 신규 Insert (Builder 사용)
    private void insertSlip(SlipInsertDTO dto) {
        // slipDate를 직접 사용
        Slip slip = Slip.builder()
                .slipDate(dto.getSlipDate())
                .venCode(dto.getVenCode())
                .slipDivision(dto.getSlipDivision())
                .actCode(dto.getActCode())
                .summaryCode(dto.getSummaryCode())
                .debit(dto.getDebit())
                .credit(dto.getCredit())
                .franCode(dto.getFranCode())
                .build();

        slipRepository.save(slip);
    }

    // 기존 Update
    private void updateSlip(SlipInsertDTO dto) {
        Slip existing = slipRepository.findById(dto.getSlipCode())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 slipCode: " + dto.getSlipCode()));

        // updateFromDTO 메서드를 통해 필요한 필드 업데이트
        existing.updateFromDTO(dto.getSlipDate(),
                dto.getVenCode(),
                dto.getSlipDivision(),
                dto.getActCode(),
                dto.getSummaryCode(),
                dto.getDebit(),
                dto.getCredit(),
                dto.getFranCode());

        slipRepository.save(existing);
    }

    // 전표 삭제
    @Transactional
    public void deleteSlipList(List<Integer> slipCodes) {
        for (Integer slipCode : slipCodes) {
            // 해당 slipCode가 존재하는 경우에만 삭제합니다.
            if (slipRepository.existsById(slipCode)) {
                slipRepository.deleteById(slipCode);
            }
        }
    }
}
