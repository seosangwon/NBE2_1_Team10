//package com.example.coffeeshop.order.service;
//
//import com.example.coffeeshop.member.domain.Member;
//import com.example.coffeeshop.member.repository.MemberRepository;
//import com.example.coffeeshop.order.domain.Order;
//import com.example.coffeeshop.order.repository.OrderRepository;
//import com.example.coffeeshop.orderitem.domain.OrderItem;
//import com.example.coffeeshop.orderitem.repository.OrderItemRepository;
//import com.example.coffeeshop.product.domain.Category;
//import com.example.coffeeshop.product.domain.Product;
//import com.example.coffeeshop.product.repository.ProductRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
////@ActiveProfiles("test")
//@SpringBootTest
//
//class OrderServiceTest {
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private OrderService orderService;
//    @Test
//    @DisplayName("주문 생성 테스트")
//    @Transactional
//    @Rollback(value = false)
//    void createOrder() {
//        Member member = new Member("qweqe@qeeq");
//        memberRepository.save(member);
//        Product product1 = new Product("커피a", Category.COFFEE_BEAN_PACKAGE,1000);
//        Product product2 = new Product("커피b", Category.COFFEE_BEAN_PACKAGE,1500);
//        productRepository.save(product1);
//        productRepository.save(product2);
//        // Map<Long, Integer> 생성 및 초기화
//        Map<Long, Integer> sampleMap = new HashMap<>();
//        sampleMap.put(product1.getProductId(), 1); // Product 1 ID와 수량 1
//        sampleMap.put(product2.getProductId(), 2); // Product 2 ID와 수량 2
//
//        // 주문 생성 테스트 로직 작성
//        //Long orderId = orderService.createOrder(sampleMap, "수원시", "12345", member.getMemberId());
//        Order order = orderRepository.findById(orderId).get();
//        List<OrderItem> orderItems =  order.getOrderItems();
//        for (OrderItem orderItem : orderItems) {
//            System.out.println(orderItem.getOrderItemId());
//        }
//        Assertions.assertThat(order.getOrderItems().size()).isEqualTo(2);
//        assertNotNull(orderId);
//    }
//}