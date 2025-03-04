package com.ohgiraffers.cafesyncfinalproject.vendor.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fran")
public class VendorController {

    private final VendorService vendorService;

    // ✅ 거래처 정보 조회
    @GetMapping("/vendor")
    public ResponseEntity<ResponseDTO> getVendorList() {
        try {
            List<VendorDTO> vendorList = vendorService.findVendorList();

            if (vendorList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "해당 거래처 정보가 없습니다.", null));
            }

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "조회 성공", vendorList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    // ✅ 거래처 등록
    @PostMapping("/vendor")
    public ResponseEntity<ResponseDTO> insertVendor(@RequestBody VendorDTO vendorDTO) {

        System.out.println("등록할 업체 정보 = " + vendorDTO);

        try {
            VendorDTO createdVendor = vendorService.createVendor(vendorDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDTO(HttpStatus.CREATED, "거래처 등록 성공", createdVendor));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "거래처 등록 실패", null));
        }
    }

    // ✅ 거래처 정보 수정 (업데이트)
    @PutMapping("/vendor/{venCode}")
    public ResponseEntity<ResponseDTO> updateVendor(
            @PathVariable("venCode") Integer venCode,
            @RequestBody VendorDTO vendorDTO) {

        System.out.println("수정할 업체 정보 = " + vendorDTO);

        try {
            VendorDTO updatedVendor = vendorService.updateVendor(venCode, vendorDTO);

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "거래처 정보 수정 성공", updatedVendor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(HttpStatus.NOT_FOUND, "해당 거래처를 찾을 수 없습니다.", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "거래처 정보 수정 실패", null));
        }
    }
}
