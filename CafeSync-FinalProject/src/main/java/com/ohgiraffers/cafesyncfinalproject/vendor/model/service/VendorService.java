package com.ohgiraffers.cafesyncfinalproject.vendor.model.service;

import com.ohgiraffers.cafesyncfinalproject.vendor.model.dao.VendorRepository;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.dto.VendorDTO;
import com.ohgiraffers.cafesyncfinalproject.vendor.model.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper; // ModelMapper 주입

    // 거래처 정보 조회
    public List<VendorDTO> findVendorList() {
        List<Vendor> vendorList = vendorRepository.findAll();
        List<VendorDTO> vendorDTOList = vendorList.stream()
                .map(vendor -> modelMapper.map(vendor, VendorDTO.class))
                .collect(Collectors.toList());
        return vendorDTOList;
    }
}
