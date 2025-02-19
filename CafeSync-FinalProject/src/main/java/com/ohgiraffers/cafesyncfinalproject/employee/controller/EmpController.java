package com.ohgiraffers.cafesyncfinalproject.employee.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "EmpController", description = "가맹점 - 직원 관리")
public class EmpController {
}
