package com.ohgiraffers.cafesyncfinalproject.account.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;

@Getter
public enum Role {
    ADMIN(1),
    USER(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    @JsonValue  // JSON 직렬화 시 숫자로 반환
    public int getValue() {
        return value;
    }

    @JsonCreator  // JSON 역직렬화 시 문자열로 변환 가능
    public static Role fromString(String key) {
        if ("ADMIN".equalsIgnoreCase(key)) return ADMIN;
        if ("USER".equalsIgnoreCase(key)) return USER;
        throw new IllegalArgumentException("잘못된 권한 값: " + key);
    }

    // DB 저장을 위한 변환기
    @Converter(autoApply = true)
    public static class RoleConverter implements AttributeConverter<Role, Integer> {
        @Override
        public Integer convertToDatabaseColumn(Role role) {
            return role != null ? role.getValue() : null;
        }

        @Override
        public Role convertToEntityAttribute(Integer dbData) {
            for (Role role : Role.values()) {
                if (role.getValue() == dbData) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Unknown database value: " + dbData);
        }
    }
}
