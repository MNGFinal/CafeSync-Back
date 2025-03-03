package com.ohgiraffers.cafesyncfinalproject.chat.model.service;

import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatRoomParticipantRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dao.ChatRoomRepository;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatRoomDTO;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.ChatRoomParticipantDTO;
import com.ohgiraffers.cafesyncfinalproject.chat.model.dto.RoomType;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRoom;
import com.ohgiraffers.cafesyncfinalproject.chat.model.entity.ChatRoomParticipant;
import com.ohgiraffers.cafesyncfinalproject.employee.model.dao.EmpRepository;
import com.ohgiraffers.cafesyncfinalproject.employee.model.entity.Employee;
import com.ohgiraffers.cafesyncfinalproject.firebase.FirebaseStorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository participantRepository;
    private final FirebaseStorageService firebaseStorageService;
    private final ModelMapper modelMapper;

    // 채팅방 생성
    @Transactional
    public ChatRoom createChatRoom(String roomName, List<Integer> memberEmpCodes) {
        Long roomId = System.currentTimeMillis();

        RoomType roomType = (memberEmpCodes.size() == 2) ? RoomType.ONE_TO_ONE : RoomType.GROUP;

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomName)
                .roomType(roomType)
                .build();

        final ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        chatRoomRepository.flush(); // ID 즉시 반영

        System.out.println("Saved chatRoom ID: " + savedChatRoom.getRoomId());

        // 🔹 `memberEmpCodes`를 Employee 리스트로 변환
        List<Employee> members = memberEmpCodes.stream()
                .map(empCode -> new Employee(empCode)) // Employee 객체 생성 (기본 생성자 필요)
                .collect(Collectors.toList());

        // ✅ 참여자 추가 (Firebase에서 프로필 이미지 변환 포함)
        List<ChatRoomParticipant> participants = members.stream()
                .map(employee -> {
                    // 🔹 Employee → EmployeeDTO 변환
                    ChatRoomParticipantDTO employeeDTO = modelMapper.map(employee, ChatRoomParticipantDTO.class);

                    // 🔹 Firebase 이미지 URL 변환
                    employeeDTO.setProfileImage(firebaseStorageService.convertGsUrlToHttp(employeeDTO.getProfileImage()));

                    // 🔹 EmployeeDTO → Employee 엔티티 다시 변환
                    Employee updatedEmployee = modelMapper.map(employeeDTO, Employee.class);

                    return new ChatRoomParticipant(savedChatRoom, updatedEmployee);
                })
                .collect(Collectors.toList());

        participantRepository.saveAll(participants);

        return savedChatRoom;
    }


    // 참여중인 채팅방 조회
    public List<ChatRoomDTO> getChatRoomsByEmpCode(Integer empCode) {
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByEmpCode(empCode);

        return chatRooms.stream().map(chatRoom -> {
            ChatRoomDTO chatRoomDTO = modelMapper.map(chatRoom, ChatRoomDTO.class);

            // ✅ 참여자 리스트 변환 (Firebase URL 적용)
            List<ChatRoomParticipantDTO> participants = chatRoom.getParticipants().stream().map(participant -> {
                ChatRoomParticipantDTO participantDTO = modelMapper.map(participant.getEmployee(), ChatRoomParticipantDTO.class);
                participantDTO.setProfileImage(firebaseStorageService.convertGsUrlToHttp(participantDTO.getProfileImage())); // ✅ 변환 적용
                return participantDTO;
            }).collect(Collectors.toList());

            chatRoomDTO.setParticipants(participants);

            return chatRoomDTO;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Integer> getParticipants(Long roomId) {
        return chatRoomRepository.findByRoomId(roomId)
                .map(chatRoom -> chatRoom.getParticipants().stream()
                        .map(participant -> participant.getEmployee().getEmpCode())
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
    }

    @Transactional
    public void removeParticipant(Long roomId, Integer empCode) {
        // JPA Native Query 등을 이용해 삭제
        participantRepository.removeNative(roomId, empCode);
        System.out.println("🚮 방에서 나가기 완료: roomId=" + roomId + ", empCode=" + empCode);
    }

    // empCode -> empName 조회
    public String findEmpNameByEmpCode(Integer empCode) {
        return participantRepository.findEmpNameByEmpCodeNative(empCode)
                .orElse("알 수 없는 사용자");
    }
}
