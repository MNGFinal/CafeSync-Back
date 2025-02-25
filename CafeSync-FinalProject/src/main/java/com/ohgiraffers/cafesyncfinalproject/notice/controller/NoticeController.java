package com.ohgiraffers.cafesyncfinalproject.notice.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.note.model.dto.NoteInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/notices")
    public ResponseEntity<ResponseDTO> insertNotice(@RequestBody NoticeInsertDTO noticeInsertDTO, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.", null));
        }
        try {
            // 서비스 호출 전에 권한 체크
            String userId = principal.getName();
            int authority = noticeService.getUserAuthority(userId); // 권한 체크

            // authority 가 1인 경우에만 등록 가능
            if (authority != 1) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseDTO(HttpStatus.FORBIDDEN, "등록 권한이 없습니다.", null));
            }

            // 등록 처리
            int noticeCode = noticeService.insertNotice(noticeInsertDTO, principal);
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "노트 등록 성공", noticeCode));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "노트 등록 실패", e.getMessage()));
        }
    }

}
