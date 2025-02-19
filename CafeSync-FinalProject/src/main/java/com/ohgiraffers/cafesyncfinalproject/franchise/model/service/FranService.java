package com.ohgiraffers.cafesyncfinalproject.franchise.model.service;

import com.ohgiraffers.cafesyncfinalproject.franchise.model.dao.FranRepository;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.Fran;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dto.FranDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranService {

    private final FranRepository franRepository;
    private final ModelMapper modelMapper;


    public List<FranDTO> findAllFran() {

        List<Fran> franList = franRepository.findAll();

        System.out.println("프랜차이즈 리스트 = " + franList);

        return franList.stream()
                .map(fran -> modelMapper.map(fran, FranDTO.class))
                .collect(Collectors.toList());
    }
}
