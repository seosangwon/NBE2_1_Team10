package com.example.coffeeshop.order.repository;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.order.domain.Order;
import com.example.coffeeshop.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // ID로 주문 조회
    Optional<Order> findByOrderId(Long id);
    // 주문 회원 email로 조회
    List<Order> findByMember(Member member);
}
