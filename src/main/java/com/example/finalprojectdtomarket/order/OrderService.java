package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.cart.Cart;
import com.example.finalprojectdtomarket.cart.CartJPARepository;
import com.example.finalprojectdtomarket.cart.CartResponse;
import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.option.OptionJPARepository;
import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.orderItem.OrderItemJPARepository;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.product.ProductJPARepository;
import com.example.finalprojectdtomarket.user.User;
import com.example.finalprojectdtomarket.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true) // lazy 로딩하려면 붙이기
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartRepo;
    private final OrderJPARepository orderRepo;
    private final OptionJPARepository optionRepo;
    private final OrderItemJPARepository orderItemRepo;

    @Transactional
    public List<CartResponse.saveFormList> orderCartList(Integer sessionUserId) {
        Boolean status = true;
        List<Cart> carts = cartRepo.findByUserIdAndStatus(sessionUserId, status);
//        System.out.println("carts = " + carts);
        
        List<CartResponse.saveFormList> saveFormList = carts.stream().map(cart ->
                new CartResponse.saveFormList(cart)).toList();
//        System.out.println("saveFormList22 = " + saveFormList);

        Integer indexNum = 1;
        for (CartResponse.saveFormList list : saveFormList) {
            list.setIndexNum(indexNum++);
        }

        // 카트 수동 롤백
        for (Cart cart : carts) {
            cart.setStatus(false);
        }

        return saveFormList;

    }

    @Transactional
    public void saveOrder(OrderRequest.SaveDTO requestDTO, User user) {

        System.out.println("값확인" + requestDTO);

        //오더 저장
        Order order = orderRepo.save(requestDTO.toOrderEntity(user));

        //오더 아이템 저장
        for (int i = 0; i < requestDTO.getOptionId().size(); i++) {
            Option option = optionRepo.findById(requestDTO.getOptionId().get(i))
                    .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다."));

            Integer quantity = requestDTO.getOrderQty().get(i);

            orderItemRepo.save(requestDTO.toOrderItemEntity(order, option, quantity));

//            order.addOrderItem(requestDTO.toOrderItemEntity(order, product, quantity));

            //상품 수량 변경 //더티체킹
            option.setQty(option.getQty() - quantity);

            //선택한 카트 딜리트
            cartRepo.deleteByCartId(requestDTO.getCartId().get(i));

        }

    }
}
