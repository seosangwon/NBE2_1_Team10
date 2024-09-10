package com.example.coffeeshop.orderitem.repository;

import com.example.coffeeshop.orderitem.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}