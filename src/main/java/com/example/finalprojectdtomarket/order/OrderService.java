package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.cart.Cart;
import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.orderItem.OrderItemJPARepository;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.product.ProductJPARepository;
import com.example.finalprojectdtomarket.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true) // lazy 로딩하려면 붙이기
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderRepo;
    private final OrderItemJPARepository orderItemRepo;
    private final ProductJPARepository productRepo;

    // 다시!! order-list
    public List<OrderResponse.ListDTO> orderList(Integer sessionUserId) {
        //여러번 도는 애 조회
        List<OrderItem> orderItemList = orderRepo.findOrderList(sessionUserId);
//        System.out.println("test " + orderItemList);

        // 1번 도는 애 조회
        // orderId가 중복되어서 촤차아악 나오길래 중복제거 (대표 물품만 1개 나오게)
        Map<Integer, OrderItem> orderDistinct =
                orderItemList.stream().collect(Collectors.toMap(
                        list -> list.getOrder().getId(),  //orderId가 키값
                        list -> list,           // 값
                        (first, second) -> first    //같은 키를 가진 요소가 있으면 첫번째 값 사용
                ));

//        System.out.println("오더디스팅트" + orderDistinct);

        // Map의 values 컬렉션을 List로 변환하여 반환
        List<OrderItem> distinctOrderList = new ArrayList<>(orderDistinct.values());
        // 주문 ID(orderId)를 기준으로 내림차순 정렬
        distinctOrderList.sort((order1, order2) -> order2.getOrder().getId().compareTo(order1.getOrder().getId()));
//        System.out.println("중복처리 됐니" + distinctOrderList);

        List<OrderResponse.ListDTO> resultList = new ArrayList<>();
        for (OrderItem orderItem : distinctOrderList) {
            resultList.add(new OrderResponse.ListDTO(orderItem, orderItemList));
        }

//        System.out.println("리절트" + resultList);

        return resultList;

    }

    public List<OrderResponse.ListDTO> orderList() {
        //여러번 도는 애 조회
        List<OrderItem> orderItemList = orderItemRepo.findAll();
        System.out.println("test " + orderItemList);

        // 1번 도는 애 조회
        // orderId가 중복되어서 촤차아악 나오길래 중복제거 (대표 물품만 1개 나오게)
        Map<Integer, OrderItem> orderDistinct =
                orderItemList.stream().collect(Collectors.toMap(
                        list -> list.getOrder().getId(),  //orderId가 키값
                        list -> list,           // 값
                        (first, second) -> first    //같은 키를 가진 요소가 있으면 첫번째 값 사용
                ));

//        System.out.println("오더디스팅트" + orderDistinct);

        // Map의 values 컬렉션을 List로 변환하여 반환
        List<OrderItem> distinctOrderList = new ArrayList<>(orderDistinct.values());
        // 주문 ID(orderId)를 기준으로 내림차순 정렬
        distinctOrderList.sort((order1, order2) -> order2.getOrder().getId().compareTo(order1.getOrder().getId()));
//        System.out.println("중복처리 됐니" + distinctOrderList);

        List<OrderResponse.ListDTO> resultList = new ArrayList<>();
        for (OrderItem orderItem : distinctOrderList) {
            resultList.add(new OrderResponse.ListDTO(orderItem, orderItemList));
        }

//        System.out.println("리절트" + resultList);

        return resultList;

    }



}
