package com.ohgiraffers.cafesyncfinalproject.franinven.controller;

import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.FranInvenDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.InOutDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.FranInvenService;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.InOutService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
public class FranInvenController {

    private final FranInvenService franInvenService;
    private final InOutService inOutService;

    // 로그인한 가맹점의 재고 목록 조회
    @GetMapping("/inven/{franCode}")
    public List<FranInvenDTO> getInventoryByFranCode(@PathVariable int franCode) {

        List<FranInvenDTO> list = franInvenService.findByFranCode(franCode);

        return list;
    }

    // 재고 수량 업데이트
    @PutMapping("/inven/update")
    public ResponseEntity<String> invenUpdate(@RequestBody List<FranInvenDTO> request) {

        // 1️⃣ 서비스 호출 → 재고 업데이트 수행
        franInvenService.invenUpdate(request);

        // 2️⃣ 성공 응답 반환
        return ResponseEntity.ok("재고 업데이트 성공");
    }

    // 재고 목록 삭제
    @DeleteMapping("/inven/delete")
    public ResponseEntity<String> invenDelete(@RequestBody List<FranInvenDTO> request) {

        System.out.println("삭제할 데이터 목록 = " + request);

        franInvenService.invenDelete(request);

        return ResponseEntity.ok("삭제 성공");
    }


    // ✅ 특정 가맹점의 입출고 내역 조회 (네이티브 쿼리 기반)
    @GetMapping("/inout/list/{franCode}")
    public ResponseEntity<List<InOutDTO>> getInOutList(@PathVariable("franCode") int franCode) {

        System.out.println("franCode = " + franCode);

        List<InOutDTO> inOutList = inOutService.getInOutList(franCode);

        return ResponseEntity.ok(inOutList);
    }

}

