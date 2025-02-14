package com.ohgiraffers.cafesyncfinalproject.home.controller;

import com.ohgiraffers.cafesyncfinalproject.home.dto.AccountDTO;
import com.ohgiraffers.cafesyncfinalproject.home.dto.FranchiseDTO;
import com.ohgiraffers.cafesyncfinalproject.home.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/find-id")
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    // 가맹점 리스트 조회 API
    @GetMapping("/findFranList")
    public ResponseEntity<List<FranchiseDTO>> getFranList() {
        List<FranchiseDTO> list = findService.getFranList();

        if (list.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content 반환
        }

        return ResponseEntity.ok(list); // 200 OK와 함께 리스트 반환
    }

    // (가맹점) 아이디 찾기 검증 API
    @PostMapping("/verifyUser")
    public ResponseEntity<?> verifyUser(@RequestBody AccountDTO request) {

        System.out.println("아이디 찾기 검증 데이터 = " + request);

        // storeCode가 null이거나 0이면 10000으로 설정
        if (request.getStoreCode() == 0) {
            request.setStoreCode(10000);
        }

        System.out.println("본사 아이디 찾기 검증 데이터 = " + request);

        String userId = findService.findUserId(request);

        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(404).body("일치하는 계정이 없습니다.");
        }
    }
}
