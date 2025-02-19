package com.ohgiraffers.cafesyncfinalproject.franchise.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.service.FranService;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.dto.FranDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hq")
public class FranController {

    public final FranService franService;

    @GetMapping("/mgment")
    public ResponseEntity<ResponseDTO> findAllFran() {

        List<FranDTO> franList = franService.findAllFran();

        System.out.println("franList컨트롤러단 = " + franList);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "가맹점 목록 조회 성공", franList);

        return ResponseEntity.ok(response);
    }

}