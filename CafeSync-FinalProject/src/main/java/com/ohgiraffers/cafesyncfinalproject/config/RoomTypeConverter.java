package com.ohgiraffers.cafesyncfinalproject.config;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.RoomType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoomTypeConverter implements AttributeConverter<RoomType, String> {

    @Override
    public String convertToDatabaseColumn(RoomType roomType) {
        if (roomType == null) return null;
        return roomType.getValue(); // ✅ '1:1' 또는 'group'으로 저장
    }

    @Override
    public RoomType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (RoomType type : RoomType.values()) {
            if (type.getValue().equals(dbData)) return type;
        }
        throw new IllegalArgumentException("Unknown RoomType: " + dbData);
    }
}
