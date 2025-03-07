package com.ohgiraffers.cafesyncfinalproject.tax.model.service;


import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranchiseDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.ActDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.SummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.tax.model.dao.TaxRepository;
import com.ohgiraffers.cafesyncfinalproject.tax.model.dto.SlipDTO;
import com.ohgiraffers.cafesyncfinalproject.tax.model.dto.TaxDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service("taxService2")
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;

    public List<TaxDTO> findFranTaxes(int franCode, String startDate, String endDate) {
        List<Object[]> results = taxRepository.findByFranCodeAndDateRange(franCode, startDate, endDate);

        return results.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaxDTO convertToDTO(Object[] row) {
        // ✅ slipDate 변환
        LocalDateTime slipDate;
        if (row[5] instanceof java.sql.Timestamp) {
            slipDate = ((java.sql.Timestamp) row[5]).toLocalDateTime();
        } else {
            LocalDate date = LocalDate.parse(row[5].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            slipDate = date.atStartOfDay(); // 2025-02-28 00:00:00
        }


        // ✅ VendorDTO 생성
        VendorDTO vendor = new VendorDTO(
                (int) row[6],
                row[7] != null ? row[7].toString() : null,
                row[8] != null ? row[8].toString() : null,
                row[9] != null ? row[9].toString() : null,
                row[10] != null ? row[10].toString() : null,
                row[11] != null ? row[11].toString() : null,
                row[12] != null ? row[12].toString() : null
        );

        // ✅ ActDTO 생성
        ActDTO act = new ActDTO(
                (int) row[14],
                row[15] != null ? row[15].toString() : null,
                row[16] != null ? row[16].toString() : null
        );

        // ✅ SummaryDTO 생성
        SummaryDTO summary = new SummaryDTO(
                row[17].toString(),
                row[18] != null ? row[18].toString() : null
        );

        // ✅ debit, credit 변환
        Integer debit = (row[19] instanceof Number) ? ((Number) row[19]).intValue() : null;
        Integer credit = (row[20] instanceof Number) ? ((Number) row[20]).intValue() : null;

        // ✅ FranchiseDTO (가맹점 정보) 변환
        FranchiseDTO franchise = new FranchiseDTO(
                (int) row[4], // franCode
                row[21] != null ? row[21].toString() : null, // franName
                row[22] != null ? row[22].toString() : null  // franAddr
        );

        // ✅ SlipDTO 생성
        SlipDTO slipDTO = new SlipDTO(
                (int) row[2], slipDate, vendor, row[13].toString(),
                act, summary, debit, credit, (int) row[4]
        );

        // ✅ 최종 TaxDTO 생성
        return new TaxDTO(
                row[0].toString(), // taxId
                row[1].toString(), // taxDate
                (int) row[2],      // slipCode
                slipDTO,           // slip
                (int) row[3],      // taxVal
                (int) row[4],      // franCode
                franchise          // franchise 정보 추가
        );
    }

    // 세금 계산서 삭제
    @Transactional
    public boolean deleteFranTaxes(List<String> taxIds) {
        try {
            taxRepository.deleteAllById(taxIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

