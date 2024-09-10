package com.example.coffeeshop.orderitem.domain;

import com.example.coffeeshop.order.domain.Order;
import com.example.coffeeshop.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)  // Auditing 기능 활성화
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;  // 주문 항목 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // 연관된 주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // 연관된 상품

    @Column(nullable = false)
    private int quantity;  // 주문한 수량

    @Column(nullable = false)
    private long price;  // 가격 (상품 가격 * 수량)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;  // 생성일시

    @LastModifiedDate
    private LocalDateTime updatedAt;  // 수정일시

    // 생성자 (상품과 수량만 받음, 가격은 자동 계산)
    public OrderItem(Product product, int quantity, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice() * quantity;
        this.order = order;
    }

    // 수량 변경 시 가격 자동 업데이트
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.price = this.product.getPrice() * newQuantity;
        this.updatedAt = LocalDateTime.now();
    }
}