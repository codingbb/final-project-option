package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket._core.errors.exception.CartNotFoundException;
import com.example.finalprojectdtomarket._core.errors.exception.OutOfStockException;
import com.example.finalprojectdtomarket._core.errors.exception.ProductExistException;
import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.option.OptionJPARepository;
import com.example.finalprojectdtomarket.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartRepo;
    private final OptionJPARepository optionRepo;

    // cart status 조회
    public CartStatus findCartStatus() {
        CartStatus cartStatus = CartStatus.CART_ING;
        List<Cart> cartList = cartRepo.findByCartStatus(cartStatus);

        List<CartResponse.StatusDTO> statuses = cartList.stream().map(cart
                -> new CartResponse.StatusDTO(cart)).toList();

//        System.out.println("statuses = " + statuses);

        String status = "CART_BEFORE";

        for (CartResponse.StatusDTO statusDTO : statuses) {
            if (statusDTO.getStatus().equals(CartStatus.CART_ING)) {
                status = "CART_ING";
                break;
            }
        }

        return CartStatus.valueOf(status);
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
                    .orElseThrow(() -> new ProductExistException());

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
//        System.out.println("requestDTOs = " + requestDTOs);
        
        List<Integer> cartIds = requestDTOs.stream().mapToInt(value -> value.getCartId()).boxed().toList();

        List<Cart> cartList = cartRepo.findByIds(cartIds);

        for(Cart cart : cartList){
            Option option = cart.getOption();

//            System.out.println("오더 큐티와이 1 " + cart.getOrderQty());
//            System.out.println("옵션 재고 1 " + option.getQty());

            // 카트 수량이랑 status 업데이트... requestDTOs 가져오려고
            CartRequest.UpdateDTO cartMatching = requestDTOs.stream().filter(updateDTO ->
                    updateDTO.getCartId().equals(cart.getId()))
                    .findFirst()
                    .orElseThrow(() -> new CartNotFoundException());

            cart.setOrderQty(cartMatching.getOrderQty());
            cart.setStatus(cartMatching.getStatus());

            if (cart.getOrderQty() > option.getQty()) {
                throw new OutOfStockException();
            }

//            System.out.println("오더 큐티와이 2 " + cart.getOrderQty());
//            System.out.println("옵션 재고 2 " + option.getQty());

        }

//        System.out.println("Jooho 1 : in query 발동");

    }

    @Transactional
    public void cartListUpdate(CartRequest.ListUpdateDTO requestDTO) {
        List<Cart> cartList = cartRepo.findByCartStatus(requestDTO.getStatus());
//        System.out.println("cartList = " + cartList);

        for (Cart cart : cartList) {
            cart.setStatus(CartStatus.CART_BEFORE);
        }

    }
}
