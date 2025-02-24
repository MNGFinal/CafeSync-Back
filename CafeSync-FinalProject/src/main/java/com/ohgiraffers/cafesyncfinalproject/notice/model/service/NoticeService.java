package com.ohgiraffers.cafesyncfinalproject.notice.model.service;

import com.ohgiraffers.cafesyncfinalproject.notice.model.dao.NoticeRepository;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    public List<NoticeDTO> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(notice -> new NoticeDTO(
                        notice.getNoticeCode(),
                        notice.getNoticeTitle(),
                        notice.getNoticeContent(),
                        notice.getNoticeDate(),
                        notice.getNoticeViews(),
                        (notice.getAccount() != null) ? notice.getAccount().getUserId() : null, // account 에서 userId 가져오기
                        (notice.getAccount() != null && notice.getAccount().getEmployee() != null) ? notice.getAccount().getEmployee().getEmpName() : null, // empName 가져오기
                        notice.getAttachment() // 조회수 추가
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public NoticeDTO selectNoticeByNoticeCode(int noticeCode) {
        Notice notice = noticeRepository.findById(noticeCode)
                .orElseThrow(() -> new RuntimeException("공지사항이 존재하지 않습니다."));

        notice.increaseViewCount(); // 조회수 증가

        // DTO 변환
        NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

        // 수동으로 userId와 empName을 설정
        if (notice.getAccount() != null) {
            noticeDTO.setUserId(notice.getAccount().getUserId());
        }
        if (notice.getAccount() != null && notice.getAccount().getEmployee() != null) {
            noticeDTO.setEmpName(notice.getAccount().getEmployee().getEmpName());
        }

        return noticeDTO;
    }
}
