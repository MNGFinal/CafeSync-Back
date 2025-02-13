package com.ohgiraffers.cafesyncfinalproject.franchise.model;

import com.ohgiraffers.cafesyncfinalproject.franchise.model.dao.FranRepository;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.Fran;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.FranDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranService {

    private final FranRepository franRepository;
    private final ModelMapper modelMapper;


    public List<FranDTO> findAllFran() {

        List<Fran> franList = franRepository.findAll();



        return null;

    }
}
