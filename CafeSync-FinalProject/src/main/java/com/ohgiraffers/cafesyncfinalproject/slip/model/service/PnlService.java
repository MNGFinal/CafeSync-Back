package com.ohgiraffers.cafesyncfinalproject.slip.model.service;

import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.PnlRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.PnlSlipRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.PnlDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Pnl;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.PnlSlip;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PnlService {

    private final PnlRepository pnlRepository;
    private final ModelMapper modelMapper;
    private final PnlSlipRepository pnlSlipRepository;

    @Transactional
    public void createPnl(PnlDTO pnlDTO) {
        // ✅ 1. Pnl 엔티티 저장 (손익 계산서)
        Pnl pnl = Pnl.builder()
                .pnlId(pnlDTO.getPnlId()) // 손익 계산서 ID
                .period(pnlDTO.getPeriod()) // 기간
                .revenue(pnlDTO.getRevenue()) // 총 수익
                .expense(pnlDTO.getExpense()) // 총 비용
                .operProfit(pnlDTO.getOperProfit()) // 영업 이익
                .netProfit(pnlDTO.getNetProfit()) // 순이익
                .ratio(pnlDTO.getRatio()) // 비율
                .build();

        pnlRepository.save(pnl); // ✅ 손익 계산서 저장

        // ✅ 2. PnlSlip (전표 연결 테이블) 저장
        List<PnlSlip> pnlSlipList = pnlDTO.getSlipCodes().stream()
                .map(slip -> PnlSlip.builder()
                        .pnl(pnl) // ✅ 손익 계산서와 연결
                        .slipCode(slip.getSlipCode()) // ✅ 전표 ID 저장
                        .build())
                .collect(Collectors.toList());

        pnlSlipRepository.saveAll(pnlSlipList); // ✅ 한 번에 저장
    }
}
