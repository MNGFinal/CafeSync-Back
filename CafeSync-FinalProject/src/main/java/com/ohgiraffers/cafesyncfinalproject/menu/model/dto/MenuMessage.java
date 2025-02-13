package com.ohgiraffers.cafesyncfinalproject.menu.model.dto;

import lombok.*;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MenuMessage {

    private int messageCode;
    private String message;
    private Map<String, Object> result;

}
