package com.ohgiraffers.cafesyncfinalproject.slip.model.service;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.PnlRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.PnlSlipRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.SlipRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.ActDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SlipInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Pnl;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlipService {

    private final SlipRepository slipRepository;
    private final PnlRepository pnlRepository;
    private final PnlSlipRepository pnlSlipRepository;

    // 가맹점 전표 조회
    public List<SlipDTO> findFranSlips(int franCode, String startDate, String endDate) {
        List<Object[]> resultList = slipRepository.findFranSlips(franCode, startDate, endDate);

        return resultList.stream().map(obj -> new SlipDTO(
                (int) obj[0],                                    // slipCode
                obj[1] instanceof Timestamp ?
                        ((Timestamp) obj[1]).toLocalDateTime() :  // ✅ Timestamp → LocalDateTime 변환
                        ((java.sql.Date) obj[1]).toLocalDate().atStartOfDay(), // ✅ Date → LocalDateTime 변환
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
        // ✅ 손익계산서 ID 목록 조회 (String 타입 유지)
        List<String> pnlIds = pnlSlipRepository.findPnlIdsBySlipCodes(slipCodes);

        // ✅ PnlSlip 먼저 삭제
        pnlSlipRepository.deleteBySlip_SlipCodeIn(slipCodes);

        // ✅ Slip 삭제
        slipRepository.deleteBySlipCodeIn(slipCodes);

        // ✅ 손익계산서 금액 업데이트 (String 타입 유지)
        pnlIds.forEach(this::updatePnlAmounts);
    }

    @Transactional
    public void updatePnlAmounts(String pnlId) {
        // ✅ `pnlId`를 String 타입으로 유지
        Integer updatedRevenue = pnlSlipRepository.getUpdatedRevenue(pnlId);
        Integer updatedExpense = pnlSlipRepository.getUpdatedExpense(pnlId);
        int updatedNetProfit = (updatedRevenue != null ? updatedRevenue : 0) -
                (updatedExpense != null ? updatedExpense : 0);

        // ✅ 순이익률 계산 (수익이 0이면 0%)
        String updatedRatio = (updatedRevenue != null && updatedRevenue > 0) ?
                String.format("%.2f%%", (updatedNetProfit * 100.0) / updatedRevenue) : "0.00%";

        // ✅ `pnlId` 타입을 String으로 유지
        pnlRepository.updatePnlAmounts(pnlId, updatedRevenue, updatedExpense, updatedNetProfit, updatedRatio);
    }

}
