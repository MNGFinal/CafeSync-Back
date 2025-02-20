package com.ohgiraffers.cafesyncfinalproject.menu.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuDTO;
import com.ohgiraffers.cafesyncfinalproject.menu.model.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fran")
@Tag(name = "메뉴관련 스웨거 연동")
public class MenuController {

    private final MenuService menuService;

    // 카테고리 코드로 메뉴 조회

    @Operation(summary = "카테고리별 메뉴 조회",
               description = "카테고리별 메뉴 전체 조회",
               responses = {
               @ApiResponse(responseCode = "200", description = "카테고리별 메뉴 조회 성공"),
               @ApiResponse(responseCode = "400", description = "카테고리별 메뉴 조회 실패")
    })
    @GetMapping("/menus/{categoryCode}")
    public ResponseEntity<ResponseDTO> getMenuList(@PathVariable("categoryCode") int categoryCode, @RequestParam("query") String query){

        System.out.println("프론트에서 넘어온 카테고리 = " + categoryCode);

        List<MenuDTO> menuList = menuService.getMenusByCategory(categoryCode, query);

        System.out.println("제발!!!! = " + menuList);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "카테고리별 메뉴 조회 성공", menuList);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "메뉴 Sold Out",
            description = "메뉴 Sold Out 설정 기능",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sold Out 설정 성공"),
                    @ApiResponse(responseCode = "400", description = "Sold Out 설정 실패")
            })
    // Sold Out 기능
    @PutMapping("/menus/{menuCode}")
    public ResponseEntity<ResponseDTO> menuSold(@PathVariable("menuCode") int menuCode) {

        System.out.println("프론트에서 넘어온 메뉴코드 = " + menuCode);

        MenuDTO menuSoldOut = menuService.menuSold(menuCode);

        System.out.println("menuSoldOut = " + menuSoldOut);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Sold Out 설정 성공", menuSoldOut);

        
        return ResponseEntity.ok(response);
    }




}
