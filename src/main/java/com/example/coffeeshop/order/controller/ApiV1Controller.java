package com.example.coffeeshop.order.controller;

import com.example.coffeeshop.order.domain.OrderStatus;
import com.example.coffeeshop.order.dto.OrderDto;
import com.example.coffeeshop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class ApiV1Controller {
    private final OrderService orderService;
    // 주문 생성
    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto.CreateRequestDto requestDto){
        OrderDto.CreateResponseDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

     //주문 조회(memberid) : Postman으로 동작 확인
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<?>> findOrderByMemberId(@PathVariable Long memberId){
        List<OrderDto.FindResponseDto> memberOrders = orderService.findOrderByMemberId(memberId);
        return ResponseEntity.ok(memberOrders);
    }

    //주문 단건 조회(orderid) : Postman으로 동작 확인
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findOrderByOrderId(@PathVariable Long orderId){
        OrderDto.FindResponseDto order = orderService.findOrderByOrderId(orderId);
        return ResponseEntity.ok(order);
    }

    // 전체 주문 목록 조회 : Postman으로 동작 확인
    @GetMapping("/order")
    public ResponseEntity<List<?>> findAllOrder(@RequestBody @Valid OrderDto.FindResponseDto responseDto){
        List<OrderDto.FindResponseDto> alllists = orderService.findAll();
        return ResponseEntity.ok(alllists);
    }

    // 주문 삭제 : Postman으로 동작 확인
    @DeleteMapping("/{orderid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderid){
        orderService.deletelOrder(orderid);
        return ResponseEntity.noContent().build();
    }
}
