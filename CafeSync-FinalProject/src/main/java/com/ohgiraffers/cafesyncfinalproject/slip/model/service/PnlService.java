package com.ohgiraffers.cafesyncfinalproject.slip.model.service;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranchiseDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.PnlRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.PnlSlipRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.*;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Pnl;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.PnlSlip;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Slip;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PnlService {

    private final PnlRepository pnlRepository;
    private final ModelMapper modelMapper;
    private final PnlSlipRepository pnlSlipRepository;

    // 손익 계산서 생성
    @Transactional
    public void createPnl(PnlDTO pnlDTO) {
        Pnl pnl = Pnl.builder()
                .pnlId(pnlDTO.getPnlId())
                .period(pnlDTO.getPeriod())
                .revenue(pnlDTO.getRevenue())
                .expense(pnlDTO.getExpense())
                .operProfit(pnlDTO.getOperProfit())
                .netProfit(pnlDTO.getNetProfit())
                .ratio(pnlDTO.getRatio())
                .franCode(pnlDTO.getFranCode())  // 추가된 부분
                .build();

        pnlRepository.save(pnl);

        List<PnlSlip> pnlSlipList = pnlDTO.getSlipCodes().stream()
                .map(slip -> PnlSlip.builder()
                        .pnl(pnl)
                        .slip(Slip.builder().slipCode(slip.getSlipCode()).build()) // ✅ Slip 객체를 생성해서 설정
                        .build())
                .toList();

        pnlSlipRepository.saveAll(pnlSlipList);
    }

    // 손익 계산서 조회
    @Transactional
    public List<PnlDTO> findFranPnls(int franCode, String startDate, String endDate) {
        List<Object[]> results = pnlRepository.findPnlsWithDetails(franCode, startDate, endDate);

        Map<String, PnlDTO> pnlMap = new HashMap<>();

        for (Object[] row : results) {
            // 0: pnl_id
            String pnlId = (String) row[0];

            if (!pnlMap.containsKey(pnlId)) {
                PnlDTO pnlDTO = new PnlDTO();
                pnlDTO.setPnlId(pnlId);
                pnlDTO.setPeriod(((java.sql.Date) row[1]).toLocalDate());
                pnlDTO.setRevenue(((Number) row[2]).intValue());      // revenue
                pnlDTO.setExpense(((Number) row[3]).intValue());        // expense
                pnlDTO.setOperProfit(((Number) row[4]).intValue());     // oper_profit
                pnlDTO.setNetProfit(((Number) row[5]).intValue());      // net_profit
                pnlDTO.setRatio((String) row[6]);
                pnlDTO.setFranCode(((Number) row[7]).intValue());       // fran_code

                // 프랜차이즈 정보 (예시로 null 체크 없이 바로 설정)
                FranchiseDTO franchise = new FranchiseDTO();
                franchise.setFranName((String) row[8]);
                franchise.setFranAddress((String) row[9]);
                pnlDTO.setFranchise(franchise);

                pnlDTO.setSlipCodes(new ArrayList<>());
                pnlMap.put(pnlId, pnlDTO);
            }

            // 10: pnl_slip_id가 존재하면 전표 매핑
            if (row[10] != null) {
                // pnl_slip_id와 slip_code도 Number로 변환
                Integer pnlSlipId = ((Number) row[10]).intValue();
                Integer slipCode = ((Number) row[11]).intValue();

                // slip_date 변환
                java.sql.Timestamp slipTimestamp = (java.sql.Timestamp) row[12];

                SlipDTO slipDTO = new SlipDTO();
                slipDTO.setSlipCode(slipCode);
                slipDTO.setSlipDate(slipTimestamp.toLocalDateTime());

                // Vendor 정보
                VendorDTO vendor = new VendorDTO();
                vendor.setVenCode(((Number) row[13]).intValue());
                vendor.setVenName((String) row[19]);
                slipDTO.setVenCode(vendor);

                slipDTO.setSlipDivision((String) row[14]);

                // Act 정보
                ActDTO act = new ActDTO();
                act.setActCode(((Number) row[15]).intValue());
                act.setActName((String) row[20]);
                act.setActDivision((String) row[21]);
                slipDTO.setActCode(act);

                // Summary 정보
                SummaryDTO summary = new SummaryDTO();
                summary.setSummaryCode((String) row[16]);
                summary.setSummaryName((String) row[22]);
                slipDTO.setSummaryCode(summary);

                slipDTO.setDebit(row[17] != null ? ((Number) row[17]).intValue() : null);
                slipDTO.setCredit(row[18] != null ? ((Number) row[18]).intValue() : null);

                PnlSlipDTO pnlSlipDTO = new PnlSlipDTO();
                pnlSlipDTO.setId(pnlSlipId);
                pnlSlipDTO.setPnlId(pnlId);
                pnlSlipDTO.setSlipCode(slipCode);
                pnlSlipDTO.setSlip(List.of(slipDTO));

                pnlMap.get(pnlId).getSlipCodes().add(pnlSlipDTO);
            }
        }

        return new ArrayList<>(pnlMap.values());
    }

    // 손익 계산서 삭제
    @Transactional
    public void deletePnlList(List<String> pnlIds) {
        pnlRepository.deleteAllById(pnlIds);
    }
}
