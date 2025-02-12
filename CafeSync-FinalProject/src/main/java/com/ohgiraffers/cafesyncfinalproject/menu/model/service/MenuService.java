package com.ohgiraffers.cafesyncfinalproject.menu.model.service;

import com.ohgiraffers.cafesyncfinalproject.menu.model.dao.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

}
