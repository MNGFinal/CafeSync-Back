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
@RequestMapping("/api")
@Tag(name = "메뉴관련 스웨거 연동")
public class MenuController {

    private final MenuService menuService;

    // 카테고리 코드로 메뉴 조회 (가맹점)

    @Operation(summary = "카테고리별 메뉴 조회",
               description = "카테고리별 메뉴 전체 조회",
               responses = {
               @ApiResponse(responseCode = "200", description = "카테고리별 메뉴 조회 성공"),
               @ApiResponse(responseCode = "400", description = "카테고리별 메뉴 조회 실패")
    })
    @GetMapping("/fran/menus/{categoryCode}")
    public ResponseEntity<ResponseDTO> getMenuList(@PathVariable("categoryCode") int categoryCode,  @RequestParam(value = "query", required = false) String query){

        System.out.println("프론트에서 넘어온 카테고리 = " + categoryCode);

        List<MenuDTO> menuList = menuService.getMenusByCategory(categoryCode, query);

        System.out.println("제발!!!! = " + menuList);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "카테고리별 메뉴 조회 성공", menuList);

        System.out.println("검색할 때 받은 값 = " + query);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK,"메뉴 조회 성공", menuList));
    }

    // Sold Out 기능 (가맹점)
    @Operation(summary = "메뉴 Sold Out",
            description = "메뉴 Sold Out 설정 기능",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sold Out 설정 성공"),
                    @ApiResponse(responseCode = "400", description = "Sold Out 설정 실패")
            })
    @PutMapping("/fran/menus/{menuCode}")
    public ResponseEntity<ResponseDTO> menuSold(@PathVariable("menuCode") int menuCode) {

        System.out.println("프론트에서 넘어온 메뉴코드 = " + menuCode);

        MenuDTO menuSoldOut = menuService.menuSold(menuCode);

        System.out.println("menuSoldOut = " + menuSoldOut);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Sold Out 설정 성공", menuSoldOut);

        
        return ResponseEntity.ok(response);
    }

    // 메뉴 수정 (본사)
    @Operation(summary = "메뉴 수정(본사)",
               description = "본사 메뉴 수정 기능",
               responses = {
                       @ApiResponse(responseCode = "200", description = "메뉴 수정 성공"),
                       @ApiResponse(responseCode = "400", description = "잘못된 요청 형식"),
                       @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음"),
                       @ApiResponse(responseCode = "500", description = "서버 내부 오류")
               })
    @PutMapping("/hq/menus/{menuCode}")
    public ResponseEntity<ResponseDTO> modifyMenu(@PathVariable int menuCode, @RequestBody MenuDTO menuDTO) {

        System.out.println("프론트에서 받은 메뉴코드 = " + menuCode);
        System.out.println("단종 데이터 확인 = " + menuDTO);

        MenuDTO menuData = menuService.modifyMenu(menuDTO);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK,"메뉴 수정 성공", menuData));

    }

    // 메뉴 삭제 (본사)
    @Operation(summary = "메뉴 삭제(본사)",
               description = "본사 메뉴 삭제 기능",
               responses = {
                       @ApiResponse(responseCode = "200", description = "메뉴 삭제 성공"),
                       @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 menuCode)"),
                       @ApiResponse(responseCode = "404", description = "삭제하려는 메뉴를 찾을 수 없음"),
                       @ApiResponse(responseCode = "500", description = "서버 내부 오류")
               })
    @DeleteMapping("hq/menus/{menuCode}")
    public ResponseEntity<ResponseDTO> deleteMenu(@PathVariable int menuCode) {
        menuService.deleteMenu(menuCode);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "메뉴 삭제 성공", null));
    }

    // 메뉴 등록 (본사)
    @Operation(summary = "메뉴 등록(본사)",
               description = "본사 메뉴 등록 기능",
               responses = {
                       @ApiResponse(responseCode = "200", description = "메뉴 등록 성공"),
                       @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검사 실패)"),
                       @ApiResponse(responseCode = "401", description = "인증 실패 (로그인이 필요함)"),
                       @ApiResponse(responseCode = "403", description = "권한 없음 (관리자만 등록 가능)"),
                       @ApiResponse(responseCode = "500", description = "서버 내부 오류")
               })
    @PostMapping("hq/menus/regist")
    public ResponseEntity<ResponseDTO> registMenu(@RequestBody MenuDTO menuDTO) {

        MenuDTO menuData = menuService.registMenu(menuDTO);

        System.out.println("menuData = " + menuData);

        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "메뉴 등록 성공", menuData));
    }
}
