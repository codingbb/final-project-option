package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.cart.Cart;
import com.example.finalprojectdtomarket.cart.CartJPARepository;
import com.example.finalprojectdtomarket.cart.CartResponse;
import com.example.finalprojectdtomarket.cart.CartStatus;
import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.option.OptionJPARepository;
import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.orderItem.OrderItemJPARepository;
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
    private final CartJPARepository cartRepo;
    private final OrderJPARepository orderRepo;
    private final OptionJPARepository optionRepo;
    private final OrderItemJPARepository orderItemRepo;

    @Transactional
    public List<CartResponse.saveFormList> orderCartList(Integer sessionUserId) {

        System.out.println("7. orderCartList 메소드 실행 중");

        CartStatus status = CartStatus.CART_ING;
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
//        for (Cart cart : carts) {
//            cart.setStatus(false);
//        }

        return saveFormList;

    }

    @Transactional
    public void saveOrder(OrderRequest.SaveDTO requestDTO, User user) {

        System.out.println("11. OrderService의 saveOrder 시작");
//        System.out.println("값확인" + requestDTO);

        //오더 저장
        Order order = orderRepo.save(requestDTO.toOrderEntity(user));
        System.out.println("12. 일단 order를 저장함");

        //오더 아이템 저장
        for (int i = 0; i < requestDTO.getOptionId().size(); i++) {
            Option option = optionRepo.findById(requestDTO.getOptionId().get(i))
                    .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다."));

            System.out.println("13. 없는 상품을 저장하려고 할 수도 있으니까 option을 findById 해주기");

            Integer quantity = requestDTO.getOrderQty().get(i);

            orderItemRepo.save(requestDTO.toOrderItemEntity(order, option, quantity));

            System.out.println("14. 여기서 orderItem을 save!!");

//            order.addOrderItem(requestDTO.toOrderItemEntity(order, product, quantity));

            //상품 수량 변경 //더티체킹
            option.setQty(option.getQty() - quantity);
            System.out.println("15. option.setQty 해서 구매한만큼 상품 수량 변경! 더티체킹");

            //선택한 카트 딜리트
            cartRepo.deleteByCartId(requestDTO.getCartId().get(i));
            System.out.println("16. 구매 완료한 cart 삭제하기");

        }

    }

    // order-list
    public List<OrderResponse.ListDTO> orderList(Integer sessionUserId) {
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

    @Transactional
    public void orderCancel(List<OrderRequest.CancelDTO> requestDTO) {
        OrderStatus status;

        //값이 1개밖에 안들어오니까 (중복제거 해놔서) 해당 값으로 orderItem을 찾음
        for (int i = 0; i < requestDTO.size(); i++) {
            List<OrderItem> orderItemList = orderItemRepo.findByOrderId(requestDTO.get(i).getOrderId());
//            System.out.println("아이템리스트 " + orderItemList);

            status = OrderStatus.ORDER_CANCEL;

            //안쪽에서 for문 돌려야함
            for (OrderItem orderItem : orderItemList) {
                // order 테이블 상태변경
                orderItem.getOrder().setStatus(status);

                // product 테이블 재고 변경
                Integer productQty = orderItem.getOption().getQty();
                System.out.println("재고" + productQty);
                orderItem.getOption().setQty(productQty + orderItem.getOrderQty());
            }

        }

    }
}
