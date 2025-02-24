package com.ohgiraffers.cafesyncfinalproject.notice.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지사항 노트관련 스웨거 연동")
@RestController
@RequestMapping("/api/fran")
public class NoticeController {

    private NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    @Operation( summary = "공지사항 전체조회", description = "노트 목록의 전체 조회")
    @GetMapping("/notices")
    public ResponseEntity<ResponseDTO> getAllNotices(){

        List<NoticeDTO> getAllNotices = noticeService.getAllNotices();
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"공지사항 전체 조회성공",getAllNotices));
    }

    @Operation(summary  = "공지사항 상세정보 조회", description = "공지사항의 상세정보 조회")
    @GetMapping("/notices/{noticeCode}")
    public ResponseEntity<ResponseDTO> selectNoticeByNoteCode(@Parameter(description = "공지사항 코드", example = "1") @PathVariable int noticeCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 상세정보 조회 성공",  noticeService.selectNoticeByNoticeCode(noticeCode)));
    }

    @Operation(summary ="공지사항 검색", description = "검색 조건에 맞는 공지사항을 조회")
    @GetMapping("/notices/search")
    public ResponseEntity<ResponseDTO> selectSearchProductList(
            @RequestParam(name = "search", defaultValue = "all") String search){

        return ResponseEntity
                .ok().body(new ResponseDTO(HttpStatus.OK, "노트 검색 성공", noticeService.selectNoticeBySearch(search)));
    }
}
