package com.example.coffeeshop.product.DTO;

import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;

    @NotBlank(message = "상품명은 필수항목입니다.")
    String productName;

    @NotNull(message = "카테고리는 필수 항목입니다.")
    Category category;

    @NotNull(message = "가격은 필수항목입니다.")
    Long price;

    String description;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static ProductDTO of(Product product){
        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .category(product.getCategory())
                .price(product.getPrice())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

     public Product toEntity() {
        return Product.builder()
                .productId(this.productId)
                .productName(this.productName)
                .category(this.category)
                .price(this.price)
                .description(this.description)
                .build();
    }

}