package com.ohgiraffers.cafesyncfinalproject.menu.controller;

import com.ohgiraffers.cafesyncfinalproject.menu.model.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor // 사용함으로 생성자 주입 안해도 됨
public class MenuController {

    private final MenuService menuService;



}