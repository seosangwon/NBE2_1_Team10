package com.example.coffeeshop.domain;

import com.example.coffeeshop.domain.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private Category category;
    private long price; // cent포함으로 long
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt; // 업데이트 날짜

    public Product(Long productId, String productName, Category category, long price){
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public void setPrice(long price) {
        this.price = price;
        this.updateAt = LocalDateTime.now();
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updateAt = LocalDateTime.now();
    }

    public void setProductName(String productName) {
        this.productName = productName;
        this.updateAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updateAt = LocalDateTime.now();
    }
}


