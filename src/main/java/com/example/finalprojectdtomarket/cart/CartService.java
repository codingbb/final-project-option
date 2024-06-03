package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket._core.errors.exception.ApiException400;
import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.option.OptionJPARepository;
import com.example.finalprojectdtomarket.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartRepo;
    private final OptionJPARepository optionRepo;

    // cart status 조회
    public String findCartStatus() {
        CartStatus cartStatus = CartStatus.CART_ING;
        List<Cart> cartList = cartRepo.findByCartStatus(cartStatus);

        String status = "CART_BEFORE";

        List<CartResponse.StatusDTO> statuses = cartList.stream().map(cart -> new CartResponse.StatusDTO(cart)).toList();

        for (CartResponse.StatusDTO statusDTO : statuses) {
            if (statusDTO.equals("CART_ING")) {
                status = "CART_ING";
            }
        }

        return status;
    }


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

            CartStatus status = CartStatus.CART_BEFORE;

            if (cart != null) {
                cart.setOrderQty(cart.getOrderQty() + requestDTO.getOrderQty());
            } else {
                cartRepo.save(requestDTO.toEntity(sessionUser, option, status));
            }

        }

    }

    @Transactional
    public void updateCart(List<CartRequest.UpdateDTO> requestDTOs) {
        System.out.println("1...................");
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            Cart cart = cartRepo.findById(requestDTO.getCartId())
                    .orElseThrow(() -> new Exception404("장바구니에 존재하지 않습니다."));

            Cart qty = cartRepo.findByQty(requestDTO.getCartId());
            if (requestDTO.getOrderQty() > qty.getOption().getQty()) {
                throw new ApiException400("재고 부족! 구매 불가");
            }

            // 카트 수량이랑 status 업데이트
            cart.setOrderQty(requestDTO.getOrderQty());
            cart.setStatus(requestDTO.getStatus());

        }


    }
}
