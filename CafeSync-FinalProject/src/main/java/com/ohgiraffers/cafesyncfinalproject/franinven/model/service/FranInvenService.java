package com.ohgiraffers.cafesyncfinalproject.franinven.model.service;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dao.FranInvenRepository;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.entity.FranInven;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranInvenService {

    private final FranInvenRepository franInvenRepository;
    private final ModelMapper modelMapper;

    public List<FranInvenDTO> findByFranCode(int franCode) {
        List<FranInven> invenList = franInvenRepository.findByFranCode(franCode);

        return invenList.stream()
                .map(inven -> modelMapper.map(inven, FranInvenDTO.class))
                .collect(Collectors.toList());
    }
}
