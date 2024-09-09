package com.example.coffeeshop;

import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;
    private Category category;

    @Test
    public void testSaveProduct(){
        // Given
        Product product = new Product(null,"Columbia Coffee", category, 5000L);
        // When
        Product savedProduct = productRepository.save(product);
        // Then
        assertThat(savedProduct.getProductId()).isEqualTo(1L);

    }


    @Test
    public void testFindById(){
        // Given
        var product = new Product(null,"Columbia Coffee",category,1000L);
        var savedProduct = productRepository.save(product);
        // When
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getProductId());
        // Then
        assertThat(foundProduct.isPresent()).isTrue();
        assertThat(foundProduct.get().getProductName()).isEqualTo("Columbia Coffee");
    }

    @Test
    public void testDelete(){
        var product = new Product(null,"Columbia Coffee",category,1000L);
        var savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getProductId());

        Optional<Product> deletedProduct = productRepository.findById(1L);
        assertThat(deletedProduct.isEmpty()).isTrue();
    }
}