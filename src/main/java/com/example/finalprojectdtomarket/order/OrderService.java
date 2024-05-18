package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.cart.Cart;
import com.example.finalprojectdtomarket.cart.CartJPARepository;
import com.example.finalprojectdtomarket.cart.CartResponse;
import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.orderItem.OrderItemJPARepository;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.product.ProductJPARepository;
import com.example.finalprojectdtomarket.user.User;
import jakarta.persistence.Query;
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
    private final OrderJPARepository orderJPARepository;
    private final OrderItemJPARepository orderItemJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ProductJPARepository productJPARepository;

    @Transactional
    public void orderCancel(List<OrderRequest.CancelDTO> requestDTO) {
        OrderStatus status;

        //값이 1개밖에 안들어오니까 (중복제거 해놔서) 해당 값으로 orderItem을 찾음
        for (int i = 0; i < requestDTO.size(); i++) {
            List<OrderItem> orderItemList = orderItemJPARepository.findByOrderId(requestDTO.get(i).getOrderId());
            System.out.println("아이템리스트 " + orderItemList);

            status = OrderStatus.ORDER_CANCEL;

            //안쪽에서 for문 돌려야함
            for (OrderItem orderItem : orderItemList) {
                // order 테이블 상태변경
                orderItem.getOrder().setStatus(status);

                // product 테이블 재고 변경
                Integer productQty = orderItem.getProduct().getQty();
//                System.out.println("재고" + productQty);
                orderItem.getProduct().setQty(productQty + orderItem.getOrderQty());
            }

        }
    }


    //order-list
    public List<OrderResponse.ListDTO> orderList(Integer sessionUserId) {
        OrderStatus status = OrderStatus.ORDER_COMPLETE;
        List<OrderItem> orderItemList = orderJPARepository.findOrderList(sessionUserId, status);
//        System.out.println("아이템 리스트 " + orderItemList);

        List<OrderResponse.ListDTO> orderList = orderItemList.stream().map(orderItem
                -> new OrderResponse.ListDTO(orderItem)).toList();
//        System.out.println("아이고 " + orderList);

        // orderId가 중복되어서 촤차아악 나오길래 중복제거 (대표 물품만 1개 나오게)
        Map<Integer, OrderResponse.ListDTO> orderDistinct =
                orderList.stream().collect(Collectors.toMap(
                        list -> list.getOrderId(),  //orderId가 키값
                        list -> list,           // 값
                        (first, second) -> first    //같은 키를 가진 요소가 있으면 첫번째 값 사용
                ));

        // Map의 values 컬렉션을 List로 변환하여 반환
        List<OrderResponse.ListDTO> distinctOrderList = new ArrayList<>(orderDistinct.values());
        // 주문 ID(orderId)를 기준으로 내림차순 정렬
        distinctOrderList.sort((order1, order2) -> order2.getOrderId().compareTo(order1.getOrderId()));

        return distinctOrderList;
//        return orderList;
    }

    //구매하기
    @Transactional
    public void saveOrder(OrderRequest.SaveDTO requestDTO, User user) {
        System.out.println("값확인" + requestDTO);

        //오더 저장 //TODO : save 부분
        Order order = orderJPARepository.save(requestDTO.toOrderEntity(user));

        //오더 아이템 저장
        for (int i = 0; i < requestDTO.getProductId().size(); i++) {
            Product product = productJPARepository.findById(requestDTO.getProductId().get(i))
                    .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다."));

            Integer quantity = requestDTO.getOrderQty().get(i);

            orderItemJPARepository.save(requestDTO.toOrderItemEntity(order, product, quantity));

//            order.addOrderItem(requestDTO.toOrderItemEntity(order, product, quantity));

            //상품 수량 변경 //더티체킹
            product.setQty(product.getQty() - quantity);

            //선택한 카트 딜리트
            cartJPARepository.deleteByCartId(requestDTO.getCartId().get(i));

        }
    }


    //order-save-form
    @Transactional
    public List<CartResponse.ListDTO> orderCartList(Integer userId) {
        Boolean isChecked = true;
        //user는 sessionUser, isChecked는 true인 list 조회
        List<Cart> carts = cartJPARepository.findByUserIdAndCheckedV2(userId, isChecked);
        List<CartResponse.ListDTO> cartList = carts.stream().map(cart -> new CartResponse.ListDTO(cart)).toList();

//        System.out.println("여기선 true인가?" + cartList.get(0).getIsChecked());
        //카트 롤백
        for (Cart cart : carts) {
            cart.setIsChecked(false);
        }

//        System.out.println("여기선 true인가?" + cartList.get(0).getIsChecked());
        return cartList;
    }



    //주문서 (카트에 있는거 들고 와서 뿌림 .. 카트를 DI)
//    @Transactional
//    public List<CartResponse.ListDTO> findByCartIdAndUserIdAndStatus(Integer userId) {
//        // 저장된 리스트를 사용자 ID와 카트 ID를 기준으로 조회
//        List<CartResponse.ListDTO> cartList = orderJPARepository.findByUserIdAndStatus(userId);
//        cartJPARepository.updateCheckedById();
//        // 조회된 주문 리스트를 DTO로 변환
//        return cartList;
//    }

    @Transactional
    public List<CartResponse.ListDTO> findByUserIdAndChecked(Integer userId, Boolean isChecked) {
        // 저장된 리스트를 사용자 ID와 상태를 기준으로 조회
        List<CartResponse.ListDTO> cartList = cartJPARepository.findByUserIdAndChecked(userId, isChecked);
        cartJPARepository.updateCheckedById();
        // 모든 조회된 항목의 isChecked 상태를 true로 업데이트
//        for (CartResponse.ListDTO cart : cartList) {
//            cartJPARepository.updateCheckedById(true, cart.getId());
//        }


//        // 조회된 카트 목록을 CartResponse.ListDTO로 변환
//        List<CartResponse.ListDTO> cartList = cartList.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());

        return cartList;
    }


}
