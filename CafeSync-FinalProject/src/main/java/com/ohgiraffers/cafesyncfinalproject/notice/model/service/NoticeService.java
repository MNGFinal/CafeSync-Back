package com.ohgiraffers.cafesyncfinalproject.notice.model.service;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dao.NoticeAccountRepository;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dao.NoticeInsertRepository;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dao.NoticeRepository;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.dto.NoticeInsertDTO;
import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.Account;
import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.Notice;
import com.ohgiraffers.cafesyncfinalproject.notice.model.entity.NoticeInsert;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeAccountRepository noticeAccountRepository;
    private final NoticeInsertRepository noticeInsertRepository;
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

        // notice.increaseViewCount(); // 조회수 증가

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

    public List<NoticeDTO> selectNoticeBySearch(String search) {
        List<Notice> noticeListWithSearchValue = noticeRepository.findByNoticeTitleContaining(search);

        List<NoticeDTO> noticeDTOList = noticeListWithSearchValue.stream()
                .map(notice -> {
                    NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);
                    noticeDTO.setUserId(notice.getAccount().getUserId());  // Account의 userId 설정
                    noticeDTO.setEmpName(notice.getAccount().getEmployee().getEmpName());  // Account의 empName 설정
                    return noticeDTO;
                })
                .collect(Collectors.toList());

        return noticeDTOList;
    }

    @Transactional
    public ResponseDTO increaseViewCount(int noticeCode) {
        Notice notice = noticeRepository.findById(noticeCode)
                .orElseThrow(() -> new RuntimeException("공지사항이 존재하지 않습니다."));

        notice.increaseViewCount(); // 조회수 증가
        noticeRepository.save(notice); // 변경된 공지사항 저장

        return new ResponseDTO(200, "조회수 증가 성공");
    }

    @Transactional
    public int insertNotice(NoticeInsertDTO noticeInsertDTO, Principal principal) {
        // 사용자 정보 조회
        Optional<Account> accountOptional = noticeAccountRepository.findById(principal.getName());
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("사용자 정보가 없습니다.");
        }
        Account account = accountOptional.get();

        // 권한이 1인 사용자만 등록 가능
        if (account.getAuthority() != 1) {
            throw new RuntimeException("등록 권한이 없습니다.");
        }

        // DTO -> Entity 변환
        NoticeInsert noticeInsert = NoticeInsert.builder()
                .noticeTitle(noticeInsertDTO.getNoticeTitle())
                .noticeContent(noticeInsertDTO.getNoticeContent())
                .noticeDate(LocalDateTime.now())  // 공지사항 작성 시간을 현재 시간으로 설정
                .noticeViews(0)  // 초기 조회수는 0
                .userId(principal.getName())  // 사용자 ID는 principal에서 가져옴
                .attachment(noticeInsertDTO.getAttachment())
                .build();

        // 공지사항 저장
        NoticeInsert savedNotice = noticeInsertRepository.save(noticeInsert);

        // 저장된 공지사항의 noticeCode 반환
        return savedNotice.getNoticeCode();
    }


    // 사용자 권한 조회 메서드
    public int getUserAuthority(String userId) {
        Optional<Account> accountOptional = noticeAccountRepository.findById(userId);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("사용자 정보가 없습니다.");
        }
        return accountOptional.get().getAuthority();
    }

    @Transactional
    public Object updateNotice(NoticeInsertDTO noticeInsertDTO) {

        // 엔티티가 존재하는지 확인
        Optional<NoticeInsert> noticeUpdateOptional = noticeInsertRepository.findById(noticeInsertDTO.getNoticeCode());

        if (!noticeUpdateOptional.isPresent()) {
            return "공지사항이 존재하지 않습니다.";
        }

        NoticeInsert noticeUpdate = noticeUpdateOptional.get();

        // 한국 시간대(KST)로 변환
        ZonedDateTime koreaTime = noticeInsertDTO.getNoticeDate().atZone(ZoneId.of("Asia/Seoul"));

        // NoteUpdate 객체의 각 필드 갱신
        noticeUpdate
                .noticeTitle(noticeInsertDTO.getNoticeTitle())
                .noticeDate(koreaTime.toLocalDateTime())  // 한국 시간으로 설정
                .noticeContent(noticeInsertDTO.getNoticeContent())
                .attachment(noticeInsertDTO.getAttachment());

        // 수정된 NoticeInsert 엔티티 저장
        noticeInsertRepository.save(noticeUpdate);

        return "공지사항 업데이트 성공";
    }

}
