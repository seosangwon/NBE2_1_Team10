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

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    // 주문하기
    public Long makeOrder(Long memberId, Long productId,String email, String address, String postcode, int quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 제품은 없는 제품입니다"+productId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new NoSuchElementException("서비스 대상에 해당하지 않는 아이디입니다."));
        OrderItem orderItem = OrderItem.createOrderItem(product, quantity);
        Order order = Order.createOrder(address,postcode,member,orderItem);
        orderRepository.save(order);
        return order.getOrderId();
    }
    // email을 통한 주문 조회
    public List<Order> findOrderByEmail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("주문한적이 없는 email입니다"));
        return orderRepository.findByMember(member);
    }

    // id를 통한 주문 조회
    public Order findOrderByOrderId(Long id){
       return orderRepository.findByOrderId(id).orElseThrow(()->new NoSuchElementException("없는 id입니다"));
    }
    // 주문 취소
    public void cancelOrder(Long orderId){
        memberRepository.deleteById(orderId);
    }
    // 주문 수정 - 필요한가? PDF상으로 회원이 주문을 수정하는 것 같지는 않음

    // 전체 주문 조회
    public List<Order> findAll(){
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new NoSuchElementException("주문이 존재하지 않습니다.");
        }
        return orders;
    }

    /**
     *
     * @param productId
     * @return
     * // 특정 상품이 포함된 주문 조회
     *     public List<Order> findByProductId(Long productId){
     *         Product product = productRepository.findById(productId)
     *                 .orElseThrow(()->new IllegalArgumentException("잘못된 productId입니다"));
     *         // 주어진 product를 포함하는 모든 orderItem 조회
     *          List<OrderItem> orderItems = orderItemRepository.findByProduct(product);
     *
     *     }
     */
}
