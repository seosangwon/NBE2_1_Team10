package com.example.coffeeshop.product.sevice;

import com.example.coffeeshop.product.DTO.ProductDTO;
import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 모든 제품 조회
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                 .map(ProductDTO::of)
                .collect(Collectors.toList());
    }

    // ID로 제품 조회 (예외 처리 포함)
    public ProductDTO findProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::of) // Product -> ProductDTO로 변환
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 제품을 찾을 수 없습니다. ID: " + id));
    }

    // 새 제품 저장
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productDTO.toEntity(); // DTO -> 엔티티 변환
        Product savedProduct = productRepository.save(product);
        return ProductDTO.of(savedProduct); // 엔티티 -> DTO 변환
    }

    // 제품 삭제 (예외 처리 포함)
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 ID의 제품을 찾을 수 없습니다. ID: " + id);
        }
        productRepository.deleteById(id);
    }

    // 카테고리별 제품 조회
    public List<ProductDTO> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductDTO::of) // Product -> ProductDTO로 변환
                .collect(Collectors.toList());
    }
}