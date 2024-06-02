package com.example.finalprojectdtomarket.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartRepo;


    // cart 조회
    public List<CartResponse.ListDTO> cartList(Integer sessionUserId) {
        List<Cart> carts = cartRepo.findByCartUserId(sessionUserId);
//        System.out.println("카트" + carts);
        List<CartResponse.ListDTO> cartList =
                carts.stream().map(cart -> new CartResponse.ListDTO(cart)).toList();

        Integer indexNum = cartList.size();
        for (CartResponse.ListDTO cart : cartList) {
            cart.setIndexNum(indexNum--);
        }

        return cartList;
    }
}
