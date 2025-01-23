package com.ohgiraffers.cafesyncfinalproject.mappers;

import com.ohgiraffers.cafesyncfinalproject.inventory.model.dto.InvenDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    List<InvenDTO> findAllInvenList();
}
