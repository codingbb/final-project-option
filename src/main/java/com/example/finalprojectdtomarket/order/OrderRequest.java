package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.orderItem.OrderItemRequest;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    @Data
    public static class SaveDTO {
        //order에 넣는 부분
        private String address;
        private Integer totalSum; //총합계
        private OrderStatus status;

        private List<Integer> productId;
        private List<String> pName;
        private List<Integer> price;  //계산된 가격

        //cart 부분
        private List<Integer> cartId;
        private List<Integer> orderQty;    //선택한 수량
        //?
        private List<Integer> orderId;


        // user 들고 오는 부분
//        private Integer userId;

        public Order toOrderEntity(User user) {
            return Order.builder()
                    .user(user)
                    .address(address)
                    .sum(totalSum)
                    .status(status)
                    .build();
        }

        public OrderItem toOrderItemEntity(Order order, Product product, Integer quantity) {
            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .orderQty(quantity)
                    .build();

        }
    }

//    @Data
//    public static class OrderItemDTO {
//        //product 들고 오는 부분
//        private Integer productId;
//        private String pName;
//        private Integer price;  //계산된 가격
//
//        //cart 부분
//        private Integer cartId;
//        private Integer orderQty;    //선택한 수량
//        //?
//        private Integer orderId;
//
//        public OrderItem toOrderItemEntity(Order order, Product product) {
//            return OrderItem.builder()
//                    .order(order)
//                    .product(product)
//                    .orderQty(orderQty)
//                    .build();
//
//        }
//
//    }


}
