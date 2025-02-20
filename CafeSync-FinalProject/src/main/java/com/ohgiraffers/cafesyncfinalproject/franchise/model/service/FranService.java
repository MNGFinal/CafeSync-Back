package com.ohgiraffers.cafesyncfinalproject.franchise.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dao.FranRepository;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.Fran;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dto.FranDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranService {

    private final FranRepository franRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService;

    public List<FranDTO> findAllFran() {

        List<Fran> franList = franRepository.findAll();

        System.out.println("프랜차이즈 리스트 = " + franList);

        return franList.stream()
                .map(fran -> {
                    FranDTO franDTO = modelMapper.map(fran, FranDTO.class); // ✅ FranDTO 변환

                    // ✅ Firebase Storage에서 이미지 URL 변환
                    if (franDTO.getFranImage() != null) {
                        franDTO.setFranImage(
                                firebaseStorageService.convertGsUrlToHttp(franDTO.getFranImage())
                        );
                    }
                    return franDTO;
                })
                .collect(Collectors.toList());
    }





}


