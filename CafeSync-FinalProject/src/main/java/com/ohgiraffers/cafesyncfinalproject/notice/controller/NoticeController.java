package com.ohgiraffers.cafesyncfinalproject.notice.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Operation(summary = "조회수 증가", description = "공지사항 조회수를 증가시킵니다.")
    @PostMapping("/notices/{noticeCode}/increase-view")
    public ResponseEntity<ResponseDTO> increaseViewCount(
            @Parameter(description = "공지사항 코드", example = "1") @PathVariable int noticeCode) {
        ResponseDTO responseDTO = noticeService.increaseViewCount(noticeCode);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "공지사항 등록", description = "공지사항을 등록")
    @PostMapping("/notices")
    public ResponseEntity<ResponseDTO> insertNotice(@RequestBody NoticeInsertDTO noticeInsertDTO) {
        System.out.println("📢 공지사항 등록 요청: " + noticeInsertDTO);

        try {
            // 🔹 서비스 호출 (userId 없이)
            int noticeCode = noticeService.insertNotice(noticeInsertDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 등록 성공", noticeCode));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "공지사항 등록 실패", e.getMessage()));
        }
    }




    @Operation(summary = "공지사항 수정", description = "공지사항 수정")
    @PutMapping(value = "/notices")
    public ResponseEntity<ResponseDTO> updateNote(@RequestBody NoticeInsertDTO noticeInsertDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 수정 성공",  noticeService.updateNotice(noticeInsertDTO)));
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항 삭제")
    @DeleteMapping("/notices/{noticeCode}")
    public ResponseEntity<ResponseDTO> deleteNotice(@Parameter(description = "공지사항 코드", example = "1") @PathVariable int noticeCode) {

        try {
            noticeService.deleteNotice(noticeCode);
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지사항 삭제 성공", null));
        } catch (Exception e) {
            e.printStackTrace(); // ✅ 전체 스택 트레이스 출력 (콘솔에서 확인 가능)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "공지사항 삭제 실패", e.getMessage()));
        }
    }
}
