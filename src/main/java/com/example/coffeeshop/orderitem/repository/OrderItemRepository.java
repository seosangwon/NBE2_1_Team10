package com.example.coffeeshop.orderitem.repository;

import com.example.coffeeshop.orderitem.domain.OrderItem;
import com.example.coffeeshop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
