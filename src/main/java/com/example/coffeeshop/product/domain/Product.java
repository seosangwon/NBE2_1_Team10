package com.example.coffeeshop.product.domain;

import jakarta.persistence.*;
import lombok.*;
import com.example.coffeeshop.product.domain.Category;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    String productName;

    @Enumerated(EnumType.STRING)
    Category category;

    Long price;

    String description;
}