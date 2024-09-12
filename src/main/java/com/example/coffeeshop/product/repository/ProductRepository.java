package com.example.coffeeshop.product.repository;

import com.example.coffeeshop.product.DTO.ProductDTO;
import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory(Category category);

    // Optional<Product> findByProductName (String productName);
}

