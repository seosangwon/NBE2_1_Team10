package com.example.coffeeshop.product.controller;

import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.sevice.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 모든 제품 목록 조회
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProduct();
    }

    //ID로 제품 조회
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> product = productService.findProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }

    // 새 제품 저장
    @PostMapping
    public Product createProduct (@RequestBody Product product){
        return productService.saveProduct(product);
    }

    // 제품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
