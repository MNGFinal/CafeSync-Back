package com.ohgiraffers.cafesyncfinalproject.menu.controller;

import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuDTO;
import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuMessage;
import com.ohgiraffers.cafesyncfinalproject.menu.model.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor // 사용함으로 생성자 주입 안해도 됨
public class MenuController {

    private final MenuService menuService;

    @GetMapping("list")
    public ResponseEntity<MenuMessage> menuList(Model model) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("Application", "json", Charset.forName("UTF-8")));

        List<MenuDTO> menuList = menuService.menuList();

        Map<String, Object> res = new HashMap<>();

        res.put("res", menuList);


        System.out.println("전체메뉴 조회" + res);
        System.out.println("메뉴 리스트" + menuList);
        return null;

    }

    @GetMapping("/{categoryCode}")
    public String categoryByMenu(Model model) {




        List<MenuDTO> menuList = menuService.menuList();

        model.addAttribute("result", menuList);

        System.out.println("카테고리 메뉴 들왔니?" + menuList);

        return "menu/detail";


    }



}