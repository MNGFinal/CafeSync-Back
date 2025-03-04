package com.ohgiraffers.cafesyncfinalproject.vendor.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService; // ✅ Firebase 추가
import com.ohgiraffers.cafesyncfinalproject.vendor.model.dao.VendorRepository;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService; // ✅ Firebase 추가

    // 거래처 정보 조회 (Firebase 이미지 URL 변환 추가)
    public List<VendorDTO> findVendorList() {
        List<Vendor> vendorList = vendorRepository.findAll();

        return vendorList.stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = modelMapper.map(vendor, VendorDTO.class);

                    // ✅ Firebase URL 변환 (vendorImage가 null이 아닐 경우)
                    if (vendorDTO.getVenImage() != null) {
                        vendorDTO.setVenImage(
                                firebaseStorageService.convertGsUrlToHttp(vendorDTO.getVenImage())
                        );
                    }
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    // 거래처 등록
    @Transactional
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = Vendor.builder()
                .venCode(vendorDTO.getVenCode())
                .venName(vendorDTO.getVenName())
                .businessNum(vendorDTO.getBusinessNum())
                .venOwner(vendorDTO.getVenOwner())
                .venAddr(vendorDTO.getVenAddr())
                .venDivision(vendorDTO.getVenDivision())
                .venImage(vendorDTO.getVenImage())
                .build();

        Vendor savedVendor = vendorRepository.save(vendor);

        return VendorDTO.builder()
                .venCode(savedVendor.getVenCode())
                .venName(savedVendor.getVenName())
                .businessNum(savedVendor.getBusinessNum())
                .venOwner(savedVendor.getVenOwner())
                .venAddr(savedVendor.getVenAddr())
                .venDivision(savedVendor.getVenDivision())
                .venImage(savedVendor.getVenImage())
                .build();
    }

    // 공급업체 수정
    @Transactional
    public VendorDTO updateVendor(Integer venCode, VendorDTO vendorDTO) {
        Vendor existingVendor = vendorRepository.findById(venCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 거래처를 찾을 수 없습니다."));

        // ✅ 기존 데이터 + 업데이트할 필드만 새로운 객체로 생성
        Vendor updatedVendor = Vendor.builder()
                .venCode(existingVendor.getVenCode()) // 기존 코드 유지
                .venName(existingVendor.getVenName()) // 거래처명 유지
                .businessNum(existingVendor.getBusinessNum()) // 사업자 번호 유지
                .venOwner(vendorDTO.getVenOwner()) // 새로운 대표자
                .venAddr(vendorDTO.getVenAddr()) // 새로운 주소
                .venDivision(vendorDTO.getVenDivision()) // 새로운 업체 구분
                .venImage(vendorDTO.getVenImage() != null && !vendorDTO.getVenImage().isEmpty()
                        ? vendorDTO.getVenImage() // 새 이미지가 있으면 적용
                        : existingVendor.getVenImage()) // 없으면 기존 이미지 유지
                .build();

        // ✅ 새로운 객체 저장 (기존 엔티티는 건드리지 않음)
        Vendor savedVendor = vendorRepository.save(updatedVendor);

        return modelMapper.map(savedVendor, VendorDTO.class);
    }
}
