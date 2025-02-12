package com.ohgiraffers.cafesyncfinalproject.inventory.model.service;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.dao.InventoryRepository;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.dto.InvenDTO;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvenService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    // 거래처별 재고 전체 조회
    public List<InvenDTO> findAllInventory() {

        List<Inventory> foundInven = inventoryRepository.findAll();

        return foundInven.stream().map(list -> modelMapper.map(list, InvenDTO.class)).collect(Collectors.toList());
    }

}
