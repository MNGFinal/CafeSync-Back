package com.ohgiraffers.cafesyncfinalproject.franinven.controller;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.FranInvenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
public class FranInvenController {

    private final FranInvenService franInvenService;

    // 로그인한 가맹점의 재고 목록 조회
    @GetMapping("/inven/{franCode}")
    public List<FranInvenDTO> getInventoryByFranCode(@PathVariable int franCode) {

        List<FranInvenDTO> list = franInvenService.findByFranCode(franCode);

        return list;
    }
}

