package com.ohgiraffers.cafesyncfinalproject.stat.model.service;

import com.ohgiraffers.cafesyncfinalproject.stat.model.dao.StatRepository;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.SalesDataDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.SalesSummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.dto.StatDTO;
import com.ohgiraffers.cafesyncfinalproject.stat.model.entity.Stat;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;

    // 특정 가맹점의 매출 조회
    public List<StatDTO> getSalesStat(int franCode) {


        List<Stat> stats = statRepository.findByFranCode(franCode);

        // 데이터가 없을 시
        if (stats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가맹점의 매출 데이터가 없습니다.");
        }

        return stats.stream()
                .map(stat -> modelMapper.map(stat, StatDTO.class))
                .collect(Collectors.toList());

    }

    public SalesSummaryDTO getSalesSummary() {
        SalesSummaryDTO summary = new SalesSummaryDTO(
                getSalesData(statRepository.getTodaySales()),
                getSalesData(statRepository.getWeeklySales()),
                getSalesData(statRepository.getMonthlySales()),
                getSalesData(statRepository.getYearlySales())
        );

        // 로그 추가해서 데이터 확인
        System.out.println("Today Sales: " + summary.getToday());
        System.out.println("Weekly Sales: " + summary.getWeek());
        System.out.println("Monthly Sales: " + summary.getMonth());
        System.out.println("Yearly Sales: " + summary.getYear());

        return summary;
    }

    private SalesDataDTO getSalesData(List<Object[]> result) {
        if (result.isEmpty()) return new SalesDataDTO(0, 0);
        Object[] data = result.get(0);
        return new SalesDataDTO(((Number) data[0]).intValue(), ((Number) data[1]).intValue());
    }
}