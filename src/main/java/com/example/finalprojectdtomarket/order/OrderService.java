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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final OrderItemJPARepository orderItemJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ProductJPARepository productJPARepository;

    @Transactional
    public void saveOrder(OrderRequest.SaveDTO requestDTO, User user) {
        System.out.println("값확인" + requestDTO);

        Order order = orderJPARepository.save(requestDTO.toOrderEntity(user));

        for (int i = 0; i < requestDTO.getProductId().size(); i++) {
            Product product = productJPARepository.findById(requestDTO.getProductId().get(i))
                    .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다."));

            Integer quantity = requestDTO.getOrderQty().get(i);

            orderItemJPARepository.save(requestDTO.toOrderItemEntity(order, product, quantity));
        }


    }


    @Transactional
    public List<CartResponse.ListDTO> orderCartList(Integer userId) {
        Boolean isChecked = true;
        //user는 sessionUser, isChecked는 true인 list 조회
        List<Cart> carts = cartJPARepository.findByUserIdAndCheckedV2(userId, isChecked);
        List<CartResponse.ListDTO> cartList = carts.stream().map(cart -> new CartResponse.ListDTO(cart)).toList();

        System.out.println("여기선 true인가?" + cartList.get(0).getIsChecked());
        //카트 롤백
        for (Cart cart : carts) {
            cart.setIsChecked(false);
        }

        System.out.println("여기선 true인가?" + cartList.get(0).getIsChecked());
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
