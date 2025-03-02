package com.ohgiraffers.cafesyncfinalproject.chat.model.dto;

public enum RoomType {
    ONE_TO_ONE("1:1"),
    GROUP("group");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
