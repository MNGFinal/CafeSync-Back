package com.ohgiraffers.cafesyncfinalproject.menu.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.Fran;
import com.ohgiraffers.cafesyncfinalproject.menu.model.dao.MenuRepository;
import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuDTO;
import com.ohgiraffers.cafesyncfinalproject.menu.model.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final FirebaseStorageService firebaseStorageService;

    // 카테고리 코드별 메뉴 조회
    public List<MenuDTO> getMenusByCategory(int categoryCode, String query) {
        System.out.println("categoryCode = " + categoryCode);
        System.out.println("query = " + query);
        List<Menu> menuList = menuRepository.findByCategoryCodeAndMenuName(categoryCode, query);
        System.out.println("메뉴리스트" + menuList);

        return menuList.stream()
                .map(menu -> {
                    MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);
                    // ✅ `menuImage` URL 변환
                    menuDTO.setMenuImage(firebaseStorageService.convertGsUrlToHttp(menu.getMenuImage()));
                    return menuDTO;
                })
                .collect(Collectors.toList());
    }

    // 메뉴 Sold Out
    @Transactional
    public MenuDTO menuSold(int menuCode) {
        // 서버에서 엔티티 타입으로 데이터 받고 -> 컨트롤러단에 넘길 때는 DTO 타입으로 변환
        Menu menuSoldOut = menuRepository.findByMenuCode(menuCode);

        System.out.println("menuSoldOut = " + menuSoldOut);

        menuSoldOut.toggleOrderableStatus();

        menuRepository.save(menuSoldOut);

        MenuDTO menuDTO = modelMapper.map(menuSoldOut, MenuDTO.class);

        return menuDTO;  //dto로 변환해서 넘김
    }

    // 메뉴 수정 (본사)
    @Transactional
    public MenuDTO modifyMenu(MenuDTO menuDTO) {

        // ✅ 기존 메뉴 찾기
        Menu menu = menuRepository.findById(menuDTO.getMenuCode())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 메뉴입니다."));

        // ✅ 메뉴 정보 업데이트
        menu.updateMenu(menuDTO);

        // ✅ 저장 후 DTO 변환하여 반환
        return modelMapper.map(menuRepository.save(menu), MenuDTO.class);

    }

}
