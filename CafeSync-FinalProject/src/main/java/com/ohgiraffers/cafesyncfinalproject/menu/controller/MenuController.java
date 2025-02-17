package com.ohgiraffers.cafesyncfinalproject.menu.controller;

import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuDTO;
import com.ohgiraffers.cafesyncfinalproject.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fran")
@CrossOrigin(origins = "http://localhost:3000") // 프론트엔드 도메인
public class MenuController {

    private final MenuService menuService;

    // 카테고리 코드로 메뉴 조회
    @GetMapping("/menus/{categoryCode}")
    public List<MenuDTO> getMenuList(@PathVariable("categoryCode") int categoryCode){

        System.out.println("프론트에서 넘어온 카테고리 = " + categoryCode);

        List<MenuDTO> menuList = menuService.getMenusByCategory(categoryCode);

        System.out.println("제발!!!! = " + menuList);

        return menuList;
    }

    // Sold Out 기능
    @PutMapping("/menus/{menuCode}")
    public MenuDTO menuSold(@PathVariable("menuCode") int menuCode) {

        System.out.println("프론트에서 넘어온 메뉴코드 = " + menuCode);

        MenuDTO menuSoldOut = menuService.menuSold(menuCode);

        System.out.println("menuSoldOut = " + menuSoldOut);

        return menuSoldOut;
    }




}
