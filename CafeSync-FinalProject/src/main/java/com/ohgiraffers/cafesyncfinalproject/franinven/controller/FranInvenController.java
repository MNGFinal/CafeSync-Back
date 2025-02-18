package com.ohgiraffers.cafesyncfinalproject.franinven.controller;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.FranInvenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/inven/update")
    public ResponseEntity<String> invenUpdate(@RequestBody List<FranInvenDTO> request) {

        System.out.println("📥 받은 요청 데이터: " + request);

        // 1️⃣ 서비스 호출 → 재고 업데이트 수행
        franInvenService.invenUpdate(request);

        // 2️⃣ 성공 응답 반환
        return ResponseEntity.ok("재고 업데이트 성공");
    }
}

