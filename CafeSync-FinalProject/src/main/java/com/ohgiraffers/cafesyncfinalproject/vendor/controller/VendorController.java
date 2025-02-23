package com.ohgiraffers.cafesyncfinalproject.vendor.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.service.VendorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fran")
public class VendorController {

    private final VendorService vendorService;

    // 거래처 정보 조회
    @GetMapping("/vendor")
    public ResponseEntity<ResponseDTO> getVendorList() {
        try {
            // VendorService에서 실제 거래처 정보를 조회합니다.
            List<VendorDTO> vendorList = vendorService.findVendorList();

            if (vendorList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "해당 거래처 정보가 없습니다.", null));
            }

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "조회 성공", vendorList));
        } catch (Exception e) {
            e.printStackTrace(); // 오류 발생 시 콘솔 출력
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }
}
