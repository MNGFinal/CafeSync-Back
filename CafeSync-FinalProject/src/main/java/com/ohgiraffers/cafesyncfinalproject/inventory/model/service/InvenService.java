package com.ohgiraffers.cafesyncfinalproject.inventory.model.service;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.dao.InventoryRepository;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.dto.InvenDTO;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.entity.Inventory;
import com.ohgiraffers.cafesyncfinalproject.mappers.InventoryMapper;
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

    public List<InvenDTO> findAllInventory() {

        List<Inventory> foundInven = inventoryRepository.findAll();

        System.out.println("서비스단 = " + foundInven);

        return foundInven.stream().map(list -> modelMapper.map(list, InvenDTO.class)).collect(Collectors.toList());
    }

}
