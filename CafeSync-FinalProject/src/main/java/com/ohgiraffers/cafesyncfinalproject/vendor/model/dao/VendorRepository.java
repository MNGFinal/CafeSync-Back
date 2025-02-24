package com.ohgiraffers.cafesyncfinalproject.vendor.model.dao;

import com.ohgiraffers.cafesyncfinalproject.vendor.model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
}
