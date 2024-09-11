package com.example.coffeeshop.order.service;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.repository.MemberRepository;
import com.example.coffeeshop.order.domain.Order;
import com.example.coffeeshop.order.repository.OrderRepository;
import com.example.coffeeshop.orderitem.domain.OrderItem;
import com.example.coffeeshop.orderitem.repository.OrderItemRepository;
import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

     // 주문하기
    @Transactional
    public Long createOrder(Map<Long, Integer> productQuantityMap, String postcode , String address, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new NoSuchElementException("멤버 없음"));
        Order order = Order.createOrder(address,postcode,member);
        // OrderItem 생성 및 추가
        for (Map.Entry<Long, Integer> entry : productQuantityMap.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            // Product 조회
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

            // OrderItem 생성
            OrderItem orderItem =OrderItem.createOrderItem(product,quantity, order);

        }
        // 주문 저장
        Order saveOrder = orderRepository.save(order);
        return saveOrder.getOrderId();
    }

    // email을 통한 주문 조회
    public List<Order> findOrderByEmail(String email){
        return orderRepository.findByMemberEmail(email);
    }

    // id를 통한 주문 조회
    public Order findOrderByOrderId(Long id){
       return orderRepository.findById(id)
               .orElseThrow(()-> new NoSuchElementException("없는 주문입니다"));
    }

    // 주문 취소
    @Transactional(readOnly = false)
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("해당 주문이 존재하지 않습니다"));
        //오더에서 member를 찾고
        Member member = order.getMember();
        // member에서 order를 지우고
        member.removeOrder(order);
        // orderRepository에서 지우기
        orderRepository.deleteById(orderId);
    }

    // 전체 주문 조회
    public List<Order> findAll(){
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new NoSuchElementException("주문이 존재하지 않습니다.");
        }
        return orders;
    }
}
