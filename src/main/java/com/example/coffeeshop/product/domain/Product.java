package com.example.coffeeshop.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(nullable = false,length = 255)
    private String productName;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private long price; // cent포함으로 long
    @Column (nullable = true, length = 255)
    private String description;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;
    @LastModifiedDate
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


