package com.example.coffeeshop.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.coffeeshop.member.domain.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class) //변경 감지 LastModifiedDate
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

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
}

