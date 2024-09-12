package com.example.coffeeshop.order.domain;

import com.example.coffeeshop.orderitem.domain.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import com.example.coffeeshop.member.domain.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class) //변경 감지 LastModifiedDate
@Getter @Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 255)
    private String postcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 255)
    private OrderStatus orderStatus;
    // 주문 생성 시간
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    // 주문 변경 시간
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    /**
     * 연관관계 편의 메서드
     * @param member
     */
    public void setMember(Member member){
        this.member = member;
       member.getOrders().add(this);
    }

    // 주문 생성 메서드
    public static Order createOrder(String address, String postcode, Member member){
        Order order = new Order();
        order.setAddress(address);
        order.setPostcode(postcode);
        order.setMember(member);
        order.setOrderStatus(OrderStatus.CHECKING); //초기 주문의 상태는 확인중이어야함
        return order;
    }

}

