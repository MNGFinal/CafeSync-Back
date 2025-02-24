package com.ohgiraffers.cafesyncfinalproject.slip.model.service;

import com.ohgiraffers.cafesyncfinalproject.slip.model.dao.TaxRepository;
import com.ohgiraffers.cafesyncfinalproject.slip.model.dto.TaxDTO;
import com.ohgiraffers.cafesyncfinalproject.slip.model.entity.Tax;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private final ModelMapper modelMapper;

    // 세금 계산서 생성 (세액 = 넘어온 금액의 10%로 계산)
    @Transactional
    public void createTax(List<TaxDTO> taxList) {

        for (TaxDTO dto : taxList) {
            // taxDate를 LocalDate로 파싱 (예: "2025-02-07")
            LocalDate taxDate = LocalDate.parse(dto.getTaxDate());

            // taxId가 비어 있으면 자동 생성 (TI-YYYYMMDD-XXXX)
            if (dto.getTaxId() == null || dto.getTaxId().isEmpty()) {
                String newTaxId = createNextTaxInvoiceNumber(taxDate);
                dto.setTaxId(newTaxId);
            }

            // 세액은 넘어온 금액(taxVal)의 10% 계산 (소수점 버림)
            int computedTaxVal = (int)(dto.getTaxVal() * 0.1);

            // 엔티티 변환 (ModelMapper 사용 또는 Builder 사용)
            Tax tax = Tax.builder()
                    .taxId(dto.getTaxId())
                    .taxDate(taxDate)
                    .slipCode(dto.getSlipCode())
                    .taxVal(computedTaxVal)
                    .franCode(dto.getFranCode()) // ✅ 추가됨
                    .build();

            taxRepository.save(tax);
        }
    }

    private String createNextTaxInvoiceNumber(LocalDate taxDate) {

        // 해당 날짜의 가장 마지막 tax_id를 가져옴
        Tax lastTax = taxRepository.findFirstByTaxDateOrderByTaxIdDesc(taxDate);
        String datePart = taxDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (lastTax == null) {
            return "TI-" + datePart + "-0001";
        }

        String lastTaxId = lastTax.getTaxId(); // 예: "TI-20250207-0007"
        // 마지막 4자리(순번) 추출
        String numericPart = lastTaxId.substring(lastTaxId.length() - 4); // "0007"
        int numeric = Integer.parseInt(numericPart);
        int nextNumeric = numeric + 1;
        String nextNumericPart = String.format("%04d", nextNumeric);
        return "TI-" + datePart + "-" + nextNumericPart;
    }
}
