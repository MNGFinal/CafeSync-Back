package com.ohgiraffers.cafesyncfinalproject.franinven.controller;

import com.ohgiraffers.cafesyncfinalproject.common.ResponseDTO;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.dto.*;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.FranInvenService;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.InOutService;
import com.ohgiraffers.cafesyncfinalproject.franinven.model.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fran")
@RequiredArgsConstructor
@Tag(name = "가맹점 재고 관리", description = "가맹점의 재고 및 발주를 관리하는 API") // ✅ Swagger 그룹 태그 추가
public class FranInvenController {

    private final FranInvenService franInvenService;
    private final InOutService inOutService;
    private final OrderService orderService;

    @Operation(summary = "가맹점 재고 목록 조회", description = "로그인한 가맹점의 재고 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "재고 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = FranInvenDTO.class)))
    @GetMapping("/inven/{franCode}")
    public List<FranInvenDTO> getInventoryByFranCode(
            @Parameter(description = "가맹점 코드", example = "101") @PathVariable int franCode) {
        return franInvenService.findByFranCode(franCode);
    }

    @Operation(summary = "재고 수량 업데이트", description = "가맹점의 재고 수량을 업데이트합니다.")
    @PutMapping("/inven/update")
    public ResponseEntity<String> invenUpdate(
            @Parameter(description = "업데이트할 재고 정보", required = true)
            @RequestBody List<FranInvenDTO> request) {
        franInvenService.invenUpdate(request);
        return ResponseEntity.ok("재고 업데이트 성공");
    }

    @Operation(summary = "재고 삭제", description = "가맹점의 재고 항목을 삭제합니다.")
    @DeleteMapping("/inven/delete")
    public ResponseEntity<String> invenDelete(
            @Parameter(description = "삭제할 재고 정보", required = true)
            @RequestBody List<FranInvenDTO> request) {
        franInvenService.invenDelete(request);
        return ResponseEntity.ok("삭제 성공");
    }

    @Operation(summary = "입출고 내역 조회", description = "가맹점의 입출고 내역을 조회합니다.")
    @GetMapping("/inout/list/{franCode}")
    public ResponseEntity<List<InOutDTO>> getInOutList(
            @Parameter(description = "가맹점 코드", example = "101") @PathVariable int franCode) {
        List<InOutDTO> inOutList = inOutService.getInOutList(franCode);
        return ResponseEntity.ok(inOutList);
    }

    @Operation(summary = "출고 등록", description = "가맹점의 출고 내역을 등록합니다.")
    @PostMapping("/inout/out-register")
    public ResponseEntity<Map<String, Object>> insertOutRegister(
            @Parameter(description = "출고할 재고 목록", required = true)
            @RequestBody List<InOutInventoryJoinDTO> request) {

        boolean isRegistered = inOutService.registerOut(request);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isRegistered);
        response.put("message", isRegistered ? "출고 등록 성공" : "출고 등록 실패");

        return isRegistered
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @Operation(summary = "입고 승인", description = "입고 요청을 승인합니다.")
    @PutMapping("/inout/approve")
    public ResponseEntity<String> inoutApprove(
            @Parameter(description = "승인할 입고 내역", required = true)
            @RequestBody List<InOutDTO> request) {
        inOutService.approveInOut(request);
        return ResponseEntity.ok("입고 승인 성공!");
    }

    @Operation(summary = "발주 신청", description = "가맹점이 발주를 신청합니다.")
    @PostMapping("/order/request")
    public ResponseEntity<String> insertOrder(
            @Parameter(description = "발주 신청할 상품 목록", required = true)
            @RequestBody List<OrderDTO> orderRequest) {
        boolean isSuccess = orderService.insertOrder(orderRequest);
        return isSuccess
                ? ResponseEntity.ok("발주 신청이 완료되었습니다.")
                : ResponseEntity.badRequest().body("발주 신청에 실패했습니다.");
    }

    @Operation(summary = "발주 신청 내역 조회", description = "가맹점의 발주 신청 내역을 조회합니다.")
    @GetMapping("/order/{franCode}")
    public ResponseEntity<ResponseDTO> getFranOrderList(
            @Parameter(description = "가맹점 코드", example = "101") @PathVariable("franCode") int franCode) {
        try {
            if (franCode <= 0) {
                return ResponseEntity
                        .badRequest()
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "가맹점 코드를 확인 할 수 없습니다", null));
            }

            List<OrderDTO> orderList = orderService.getFranOrderList(franCode);

            if (orderList == null || orderList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(new ResponseDTO(HttpStatus.NO_CONTENT, "발주 내역을 찾을 수 없습니다", null));
            }

            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "발주 내역이 성공적으로 조회되었습니다", orderList));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다", null));
        }
    }

    @Operation(summary = "발주 신청 내역 업데이트", description = "가맹점의 발주 신청 내역을 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "발주 내역 업데이트 성공",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류 발생",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PutMapping("/order/update")
    public ResponseEntity<ResponseDTO> updateFranOrderList(
            @Parameter(description = "업데이트할 발주 내역 목록", required = true)
            @RequestBody List<OrderDetailDTO> request) {

        try {
            // ✅ 요청 데이터 검증
            if (request == null || request.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "업데이트할 주문 목록이 없습니다.", null));
            }

            // ✅ 서비스 로직 실행
            orderService.updateFranOrderList(request);

            return ResponseEntity
                    .ok(new ResponseDTO(HttpStatus.OK, "발주 내역이 성공적으로 업데이트되었습니다.", null));

        } catch (IllegalArgumentException e) {
            // ✅ 유효하지 않은 데이터가 들어왔을 경우
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        } catch (Exception e) {
            // ✅ 서버 오류 발생
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.", null));
        }
    }
}
