package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.option.OptionJPARepository;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.product.ProductJPARepository;
import com.example.finalprojectdtomarket.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartRepo;
    private final OptionJPARepository optionRepo;

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

    //장바구니 저장
    @Transactional
    public void cartSave(List<CartRequest.saveDTO> requestDTOs, User sessionUser) {
        //장바구니에 존재하지 않는 상품을 넣을 수도 있으니까
        for (CartRequest.saveDTO requestDTO : requestDTOs) {
            Option option = optionRepo.findById(requestDTO.getOptionId())
                    .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다"));

            Cart cart = cartRepo.findByUserAndOption(sessionUser.getId(), requestDTO.getOptionId());

            if (cart != null) {
                cart.setOrderQty(cart.getOrderQty() + requestDTO.getOrderQty());
            } else {
                cartRepo.save(requestDTO.toEntity(sessionUser, option));
            }

        }

    }
}
