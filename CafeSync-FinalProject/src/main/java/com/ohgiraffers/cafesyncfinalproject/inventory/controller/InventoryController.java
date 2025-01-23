package com.ohgiraffers.cafesyncfinalproject.inventory.controller;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.service.InvenService;
import com.ohgiraffers.cafesyncfinalproject.inventory.model.dto.InvenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InvenService invenService;

    // 점포 재고 조회 기능
    @GetMapping("/findAllInventoryList")
    public List<InvenDTO> findAllInventory() {

        List<InvenDTO> foundInven = invenService.findAllInventory();

        System.out.println("컨트롤러단 = " + foundInven);

        return foundInven;
    }
}
