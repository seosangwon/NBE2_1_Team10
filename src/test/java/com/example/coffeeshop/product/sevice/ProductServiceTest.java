package com.example.coffeeshop.product.sevice;

import com.example.coffeeshop.product.domain.Category;
import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProduct() {
        var product1 = new Product(1L,"1", Category.COFFEE_BEAN_PACKAGE,1000L);
        var product2 = new Product(2L,"2", Category.COFFEE_BEAN_PACKAGE,2000L);
        var product3 = new Product(3L,"3", Category.COFFEE_BEAN_PACKAGE,3000L);
        var product4 = new Product(4L,"4", Category.COFFEE_BEAN_PACKAGE,4000L);
        List<Product> products = Arrays.asList(product1,product2,product3,product4);

        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getAllProduct();

        // Then
        assertThat(result).hasSize(4);
        assertThat(result.get(0).getProductName()).isEqualTo("1");
    }

    @Test
    @DisplayName("findById")
    void findProductById() {
        var product1 = new Product(1L,"1", Category.COFFEE_BEAN_PACKAGE,1000L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Optional<Product> result = productService.findProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("1",result.get().getProductName());
        assertEquals(1L,result.get().getProductId());

    }

    @Test
    void saveProduct() {
        var product = new Product(null, "Product 1", Category.COFFEE_BEAN_PACKAGE, 1000L);

        // When
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.saveProduct(product);

        // Then
        assertThat(savedProduct.getProductName()).isEqualTo("Product 1");
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);
        productService.deleteProduct(productId);


    }

    @Test
    void getProductsByCategory() {
        var product1 = new Product(1L, "Product 1", Category.COFFEE_BEAN_PACKAGE, 1000L);
        var product2 = new Product(2L, "Product 2", Category.COFFEE_BEAN_PACKAGE, 2000L);
        List<Product> products = Arrays.asList(product1, product2);

        // When
        when(productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE)).thenReturn(products);
        List<Product> result = productService.getProductsByCategory(Category.COFFEE_BEAN_PACKAGE);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCategory()).isEqualTo(Category.COFFEE_BEAN_PACKAGE);
    }
}