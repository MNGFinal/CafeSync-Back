package com.ohgiraffers.cafesyncfinalproject.home.service;

import com.ohgiraffers.cafesyncfinalproject.account.model.entity.User;
import com.ohgiraffers.cafesyncfinalproject.home.dao.AccountRepository;
import com.ohgiraffers.cafesyncfinalproject.home.dao.FranchiseRepository;
import com.ohgiraffers.cafesyncfinalproject.home.dto.AccountDTO;
import com.ohgiraffers.cafesyncfinalproject.home.dto.FranchiseDTO;
import com.ohgiraffers.cafesyncfinalproject.home.entity.Account;
import com.ohgiraffers.cafesyncfinalproject.home.entity.Franchise;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindService {

    private final FranchiseRepository franchiseRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    // 가맹점 리스트 조회 (아이디 찾기)
    public List<FranchiseDTO> getFranList() {

        // DB에서 가맹점 리스트 조회
        List<Franchise> franList = franchiseRepository.findAll();

        // Entity → DTO 변환
        return franList.stream()
                .map(fran -> modelMapper.map(fran, FranchiseDTO.class))
                .collect(Collectors.toList());
    }
    
    // 아이디찾기(가맹점 코드, 사번코드, 이메일 검증)
    public String findUserId(AccountDTO request) {

        Account user = accountRepository.findByStoreCodeAndEmpCodeAndEmail(
                request.getStoreCode(), request.getEmpCode(), request.getEmail());

        return (user != null) ? user.getUserId() : null;
    }
}
