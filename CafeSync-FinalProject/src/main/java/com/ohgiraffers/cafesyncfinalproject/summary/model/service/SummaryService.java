package com.ohgiraffers.cafesyncfinalproject.summary.model.service;

import com.ohgiraffers.cafesyncfinalproject.summary.model.dao.SummaryRepository;
import com.ohgiraffers.cafesyncfinalproject.summary.model.dto.SummaryDTO;
import com.ohgiraffers.cafesyncfinalproject.summary.model.entity.Summary;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;
    private final ModelMapper modelMapper;

    // 적요 조회
    public List<SummaryDTO> findSummaryList() {
        List<Summary> summaryList = summaryRepository.findAll();

        // ModelMapper를 사용해 Summary → SummaryDTO 변환
        List<SummaryDTO> dtoList = summaryList.stream()
                .map(summary -> modelMapper.map(summary, SummaryDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }
}
