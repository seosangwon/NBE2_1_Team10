package com.example.coffeeshop.product.repository;

import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory(Category category);
}

