package com.ohgiraffers.cafesyncfinalproject.act.model.service;

import com.ohgiraffers.cafesyncfinalproject.act.model.dao.ActRepository;
import com.ohgiraffers.cafesyncfinalproject.act.model.dto.ActDTO;
import com.ohgiraffers.cafesyncfinalproject.act.model.entity.Act;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActService {

    private final ActRepository actRepository;
    private final ModelMapper modelMapper;

    // 계정과목 조회
    public List<ActDTO> findActList() {
        List<Act> actList = actRepository.findAll();
        List<ActDTO> dtoList = actList.stream()
                .map(act -> modelMapper.map(act, ActDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }
}
