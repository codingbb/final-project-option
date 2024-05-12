package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.cart.Cart;
import com.example.finalprojectdtomarket.cart.CartJPARepository;
import com.example.finalprojectdtomarket.cart.CartResponse;
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
    private final CartJPARepository cartJPARepository;

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
