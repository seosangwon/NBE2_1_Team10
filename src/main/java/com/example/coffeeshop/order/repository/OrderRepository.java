package com.example.coffeeshop.order.repository;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 주문 id로 조회
    List<Order> findOrderByMember(Member member);
}
