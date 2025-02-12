package com.ohgiraffers.cafesyncfinalproject.menu.model.service;

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

    public List<MenuDTO> menuList() {

        List<Menu> menuList = menuRepository.findAll();

        System.out.println("메뉴 들왔니?" + menuList);


        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());



    }
}
