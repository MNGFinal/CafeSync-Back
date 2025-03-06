package com.ohgiraffers.cafesyncfinalproject.stat.model.service;

import com.ohgiraffers.cafesyncfinalproject.stat.model.dao.StatRepository;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.*;
import com.ohgiraffers.cafesyncfinalproject.stat.model.entity.Stat;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;

    private List<MonthlySalesDTO> getMonthlySalesData(List<Object[]> results) {
        List<MonthlySalesDTO> monthlySalesList = new ArrayList<>();

        for (Object[] row : results) {
            String month = (String) row[0];  // 2025-01, 2025-02
            Long sales = ((Number) row[1]).longValue();  // Object → Long 변환

            monthlySalesList.add(new MonthlySalesDTO(month, sales));
        }

        return monthlySalesList;
    }



    // 특정 가맹점의 매출 조회
    public List<StatDTO> getSalesStat(Integer franCode) {


        List<Stat> stats = statRepository.findByFranCode(franCode);

        // 데이터가 없을 시
        if (stats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가맹점의 매출 데이터가 없습니다.");
        }

        return stats.stream()
                .map(stat -> modelMapper.map(stat, StatDTO.class))
                .collect(Collectors.toList());

    }

    public SalesSummaryDTO getSalesSummary(Integer franCode, LocalDate startDate, LocalDate endDate) {
        if (franCode == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "franCode 값이 필요합니다.");
        }

        // ✅ startDate와 endDate가 없으면 기본적으로 올해 전체 데이터 조회
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = LocalDate.of(now.getYear(), 1, 1); // 올해 1월 1일
            endDate = LocalDate.of(now.getYear(), 12, 31); // 올해 12월 31일
        }

        List<MonthlySalesDTO> monthlySales = getMonthlySalesData(statRepository.getSalesByDateRange(franCode, startDate, endDate));

        SalesSummaryDTO summary = new SalesSummaryDTO(
                getSalesData(statRepository.getTodaySalesByFranCode(franCode)),
                getSalesData(statRepository.getWeeklySalesByFranCode(franCode)),
                getSalesData(statRepository.getMonthlySalesByFranCode(franCode)),
                getSalesData(statRepository.getYearlySalesByFranCode(franCode)),
                monthlySales
        );

        return summary;
    }

    // 오늘/주간/월간/연간 매출을 조회하는 쿼리
    private SalesDataDTO getSalesData(List<Object[]> result) {
        if (result.isEmpty()) return new SalesDataDTO(0, 0);
        Object[] data = result.get(0);
        return new SalesDataDTO(((Number) data[0]).intValue(), ((Number) data[1]).intValue());
    }

    // 메뉴별 통계 쿼리
    // ✅ 특정 가맹점의 메뉴별 판매량 조회 (기본값: 올해 전체 데이터)
    public List<MenuSalesDTO> getMenuSales(Integer franCode, LocalDate startDate, LocalDate endDate) {
        if (franCode == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가맹점 코드가 필요합니다.");
        }

        // ✅ 기본 날짜 범위 (현재 연도 전체)
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = LocalDate.of(now.getYear(), 1, 1);
            endDate = LocalDate.of(now.getYear(), 12, 31);
        }

        // ✅ 레포지토리에서 데이터 조회
        List<Object[]> results = statRepository.getMenuSalesStats(franCode, startDate, endDate);

        // ✅ DTO로 변환 후 반환
        return results.stream()
                .map(obj -> new MenuSalesDTO(
                        (String) obj[0],   // 메뉴 이름
                        ((Number) obj[1]).intValue()  // 판매량
                ))
                .collect(Collectors.toList());
    }

}