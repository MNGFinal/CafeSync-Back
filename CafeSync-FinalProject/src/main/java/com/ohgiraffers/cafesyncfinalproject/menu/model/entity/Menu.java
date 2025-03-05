package com.ohgiraffers.cafesyncfinalproject.menu.model.entity;

import com.ohgiraffers.cafesyncfinalproject.menu.model.dto.MenuDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_menu")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name_ko")
    private String menuNameKo;      // 메뉴 이름(한글)

    @Column(name = "menu_name_en")
    private String menuNameEN;      // 메뉴 이름(영어)

    @Column(name = "menu_detail")
    private String menuDetail;      // 메뉴 설명

    @Column(name = "menu_image")
    private String menuImage;       // 메뉴 이미지

    @Column(name = "orderable_status")
    private Boolean orderableStatus;// 판매여부

    @Column(name = "category_code")
    private int categoryCode;       // 카테고리 코드

    @Column(name = "discon_status")
    private Boolean disconStatus;   // 단종 여부

    @Column(name = "season_menu")
    private int seasonMenu;         // 시즌 메뉴

    public void toggleOrderableStatus() {
        this.orderableStatus = !this.orderableStatus;
    }

    // ✅ 값 변경을 위한 메서드 (Setter 대신 사용)
    public void updateMenu(MenuDTO menuDTO) {
        this.menuNameKo = menuDTO.getMenuNameKo();
        this.menuNameEN = menuDTO.getMenuNameEN();
        this.menuDetail = menuDTO.getMenuDetail();
        this.menuImage = menuDTO.getMenuImage();
        this.disconStatus = menuDTO.getDisconStatus();  // 단종 여부도 업데이트
    }
}
