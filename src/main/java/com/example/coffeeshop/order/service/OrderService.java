package com.example.coffeeshop.order.service;

import com.example.coffeeshop.member.domain.Member;
import com.example.coffeeshop.member.repository.MemberRepository;
import com.example.coffeeshop.order.domain.Order;
import com.example.coffeeshop.order.domain.OrderStatus;
import com.example.coffeeshop.order.dto.OrderDto;
import com.example.coffeeshop.order.repository.OrderRepository;
import com.example.coffeeshop.orderitem.domain.OrderItem;
import com.example.coffeeshop.orderitem.repository.OrderItemRepository;
import com.example.coffeeshop.product.domain.Product;
import com.example.coffeeshop.product.repository.ProductRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
     // 주문하기
    @Transactional
    public OrderDto.CreateResponseDto createOrder(OrderDto.CreateRequestDto requestDto){
        int totalPrice = 0;
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(()->new NoSuchElementException("멤버 없음"));
        Order order = Order.createOrder(requestDto.getAddress(), requestDto.getPostcode(), member);
        // OrderItem 생성 및 추가
        for (Map.Entry<Long, Integer> entry : requestDto.getProductQuantityMap().entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();
            // Product 조회
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
            totalPrice += product.getPrice()*quantity;
            // OrderItem 생성
            OrderItem orderItem =OrderItem.createOrderItem(product,quantity, order);
        }
        // 주문 저장
        Order saveOrder = orderRepository.save(order);
        OrderDto.CreateResponseDto responseDto = new OrderDto.CreateResponseDto();
        responseDto.setOrderId(saveOrder.getOrderId());
        responseDto.setAddress(saveOrder.getAddress());
        responseDto.setPostcode(saveOrder.getPostcode());
        responseDto.setCreatedAt(saveOrder.getCreatedAt());
        responseDto.setTotalPrice(totalPrice);
        return responseDto;
    }
    // 멤버 id를 통한 주문 조회
    public List<OrderDto.FindResponseDto> findOrderByMemberId(Long memberId){
       Member member = memberRepository.findById(memberId)
               .orElseThrow(()->new NoSuchElementException("없는 회원입니다"));
        List<Order> orders =  orderRepository.findOrderByMember(member);
       List<OrderDto.FindResponseDto> findLists = new ArrayList<>();
        for (Order order : orders) {
            OrderDto.FindResponseDto dto = new OrderDto.FindResponseDto();
            dto.setOrderId(order.getOrderId());
            dto.setPostcode(order.getPostcode());
            dto.setAddress(order.getAddress());
            dto.setCreatedAt(order.getCreatedAt());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setMemberId(order.getMember().getMemberId());
            findLists.add(dto);
        }
        return findLists;
    }
    // 주문 id를 통한 주문 조회
    public OrderDto.FindResponseDto findOrderByOrderId(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NoSuchElementException("없는 회원입니다"));
        Long memberId = order.getMember().getMemberId();
        OrderDto.FindResponseDto findDto = new OrderDto.FindResponseDto();
        findDto.setCreatedAt(order.getCreatedAt());
        findDto.setOrderId(order.getOrderId());
        findDto.setOrderStatus(order.getOrderStatus());
        findDto.setAddress(order.getAddress());
        findDto.setPostcode(order.getPostcode());
        findDto.setMemberId(memberId);
        return findDto;
    }

    // 주문 삭제
    @Transactional
    public void deletelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("해당 주문이 존재하지 않습니다"));
        // orderRepository에서 지우기
        orderRepository.delete(order);
    }

    // 전체 주문 조회
    public List<OrderDto.FindResponseDto> findAll(){
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new NoSuchElementException("주문이 존재하지 않습니다.");
        }
        List<OrderDto.FindResponseDto> findlists = new ArrayList<>();
        for (Order order : orders) {
            OrderDto.FindResponseDto finds = new OrderDto.FindResponseDto();
            finds.setOrderId(order.getOrderId());
            finds.setMemberId(order.getMember().getMemberId());
            finds.setOrderStatus(order.getOrderStatus());
            finds.setCreatedAt(order.getCreatedAt());
            finds.setAddress(order.getAddress());
            finds.setPostcode(order.getPostcode());
            findlists.add(finds);
        }
        return findlists;
    }
    // 12시가 지나면, 주문 상태 변경
    @Scheduled(cron = "0 0 14 * * ?") // 매일 오후 14시에 실행
    public void updateOrderStatus(){
        List<Order> orders = orderRepository.findAll();  // 모든 주문 조회
        LocalDateTime now = LocalDateTime.now();

        for (Order order : orders) {
            if ((order.getCreatedAt().isBefore(now.withHour(14).withMinute(0)))
                &&(order.getOrderStatus() == OrderStatus.CHECKING)) {
                order.setOrderStatus(OrderStatus.COMPLETE);  // 상태 변경
                orderRepository.save(order);
            }
        }
    }
}
