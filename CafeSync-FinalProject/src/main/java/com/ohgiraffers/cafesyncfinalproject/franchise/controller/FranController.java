package com.ohgiraffers.cafesyncfinalproject.franchise.controller;

import com.ohgiraffers.cafesyncfinalproject.franchise.model.FranService;
import com.ohgiraffers.cafesyncfinalproject.franchise.model.entity.FranDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hq")
public class FranController {

    public final FranService franService;

    @GetMapping("/mgment")
    public List<FranDTO> findAllFran() {

        List<FranDTO> franList = franService.findAllFran();

        System.out.println("franList컨트롤러단 = " + franList);


        return franList;
    }


}
