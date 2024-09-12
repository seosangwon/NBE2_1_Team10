package com.example.coffeeshop.member.domain;

import com.example.coffeeshop.order.domain.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addOrder(Order order) {
        orders.add(order);
        order.setMember(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setMember(null);
    }

    public Member(String email) {
        this.email = email;
    }
}