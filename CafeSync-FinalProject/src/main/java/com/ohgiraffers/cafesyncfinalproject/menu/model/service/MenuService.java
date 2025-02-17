package com.ohgiraffers.cafesyncfinalproject.menu.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
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

    public List<MenuDTO> getMenusByCategory(int categoryCode) {
        List<Menu> menuList = menuRepository.findByCategoryCode(categoryCode);

        return menuList.stream()
                .map(menu -> {
                    MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);
                    // ✅ `menuImage` URL 변환
                    menuDTO.setMenuImage(firebaseStorageService.convertGsUrlToHttp(menu.getMenuImage()));
                    return menuDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuDTO menuSold(int menuCode) {
        // 서버에서 엔티티 타입으로 데이터 받고 -> 컨트롤러단에 넘길 때는 DTO 타입으로 변환
        Menu menuSoldOut = menuRepository.findByMenuCode(menuCode);

        System.out.println("menuSoldOut = " + menuSoldOut);

        menuSoldOut.toggleOrderableStatus();

        menuRepository.save(menuSoldOut);

        ModelMapper modelMapper = new ModelMapper();


        MenuDTO menuDTO = modelMapper.map(menuSoldOut, MenuDTO.class);

        return menuDTO;  //dto로 변환해서 넘김
    }
}
