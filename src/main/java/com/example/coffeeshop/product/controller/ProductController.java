package com.example.coffeeshop.product.controller;

import com.example.coffeeshop.product.DTO.ProductDTO;
import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.sevice.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 모든 제품 목록 조회
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // ID로 제품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    // 새 제품 저장
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO ProductDTO) {
        ProductDTO savedProduct = productService.saveProduct(ProductDTO);
        return ResponseEntity.ok(savedProduct);
    }

    // 제품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 카테고리별 제품 조회
    @GetMapping("/category/{category}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(Category.valueOf(category));
    }

    // 제품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
}