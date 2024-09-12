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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orderitems")
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

    // Order와 참조 순환 발생
    public static OrderItem createOrderItem(Product product, int quantity, Order order){
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.addOrderItem(order);
        orderItem.setPrice(product.getPrice()*quantity);
        return orderItem;
    }

    /**
     * 연관관계 편의 메소드
     * @param order
     */
    public void addOrderItem(Order order){
        setOrder(order);
        order.getOrderItems().add(this);
    }

    // 수량 변경 시 가격 자동 업데이트
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.price = this.product.getPrice() * newQuantity;
    }
}