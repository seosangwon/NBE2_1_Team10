package com.example.coffeeshop.order.dto;

import com.example.coffeeshop.order.domain.Order;
import com.example.coffeeshop.order.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

public class OrderDto {
    @Data
    public static class CreateRequestDto{
       private Map<Long, Integer> productQuantityMap;
        private String postcode;
        private String address;
        private Long memberId;
    }
    @Data
    public static class CreateResponseDto {
        private Long orderId;
        private String address;
        private String postcode;
        private LocalDateTime createdAt;
        private int totalPrice;
    }
    @Data
    public static class FindResponseDto{
        private Long orderId;
        private String address;
        private String postcode;
        private LocalDateTime createdAt;
        private OrderStatus orderStatus;
        private Long memberId;
    }
    @Data
    public static class OrderStatusDto{
        private OrderStatus orderStatus;
    }
}
