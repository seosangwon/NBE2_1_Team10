package com.example.coffeeshop.product.sevice;

import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // 모든 제품 조회
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    // ID로 제품 조회
    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    // 새로운 제품 저장
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    // 제품 삭제
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
