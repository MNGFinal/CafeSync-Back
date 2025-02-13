package com.ohgiraffers.cafesyncfinalproject.menu.model.service;

import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import com.ohgiraffers.cafesyncfinalproject.menu.model.dao.MenuRepository;
import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuDTO;
import com.ohgiraffers.cafesyncfinalproject.menu.model.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
