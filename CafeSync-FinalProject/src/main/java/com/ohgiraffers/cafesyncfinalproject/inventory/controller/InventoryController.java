package com.ohgiraffers.cafesyncfinalproject.inventory.controller;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.service.InvenService;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.dto.InvenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InvenService invenService;

    // 거래처별 재고 전체 조회
    @GetMapping("/findAllInventoryList")
    public List<InvenDTO> findAllInventory() {

        List<InvenDTO> foundInven = invenService.findAllInventory();

        return foundInven;
    }

}
