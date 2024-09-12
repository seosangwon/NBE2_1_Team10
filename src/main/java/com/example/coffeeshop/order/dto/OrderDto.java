package com.example.coffeeshop.order.dto;

import com.example.coffeeshop.order.domain.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

public class OrderDto {
    @Data
    public static class CreateRequestDto{
        @NotNull(message = "물품과 수량은 공백일 수 없습니다")
        @NotEmpty(message = "물품과 수량은 둘 다 1개 이상이어야합니다")
        private Map<Long, Integer> productQuantityMap;

        @NotBlank(message = "우편번호는 공백이 허용되지 않습니다")
        private String postcode;

        @NotBlank(message = "주소는 공백이 허용되지 않습니다")
        private String address;

        @NotNull(message = "Member ID 공백이 허용되지 않습니다")
        private Long memberId;
    }
    @Data
    public static class CreateResponseDto {
        private Long orderId;

        @NotBlank(message = "주소는 공백이 허용되지 않습니다")
        private String address;

        @NotBlank(message = "우편번호는 공백이 허용되지 않습니다")
        private String postcode;

        private LocalDateTime createdAt;

        @Min(value = 0, message = "최소 주문금액은 0원 이상이어야 합니다")
        private int totalPrice;

    }
    @Data
    public static class FindResponseDto{
        @NotNull(message = "Order Id는 공백이 허용되지 않습니다")
        private Long orderId;

        @NotBlank(message = "주소는 공백이 허용되지 않습니다")
        private String address;

        @NotBlank(message = "우편번호는 공백이 허용되지 않습니다")
        private String postcode;

        private LocalDateTime createdAt;

        @NotNull(message = "주문 상태는 공백이 허용되지 않습니다")
        private OrderStatus orderStatus;

        @NotNull(message = "Member ID 공백이 허용되지 않습니다")
        private Long memberId;
    }
}
