package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.product.Product;
import lombok.Data;

import java.time.LocalDate;

public class OrderResponse {

    @Data
    public static class ListDTO {
        private Integer orderId;    //order PK
        private String pName;
        private Integer sum;     //order
        private OrderStatus status; //이거 타입 보류
        private Integer orderQty;
        private Integer productId;
        private LocalDate createdAt;
        private String img;


        public ListDTO(OrderItem orderItem) {
            this.orderId = orderItem.getOrder().getId();
            this.pName = orderItem.getProduct().getName(); // lazy 로딩
            this.sum = orderItem.getOrder().getSum();
            this.status = orderItem.getOrder().getStatus();
            this.orderQty = orderItem.getOrderQty();
            this.productId = orderItem.getProduct().getId();
            this.createdAt = orderItem.getOrder().getCreatedAt().toLocalDateTime().toLocalDate();
            this.img = orderItem.getProduct().getImg();

        }

        // Lombok에서 생성된 getStatus() 메서드를 오버라이드
        public String getStatus() {
            if (OrderStatus.ORDER_COMPLETE.equals(status)) {
                return "주문완료";
            }
            if (OrderStatus.ORDER_CANCEL.equals(status)) {
                return "취소완료";
            }
            return "다시 확인하세요";
        }
    }



    //주문서 확인폼
//    @Data
//    public static class OrderSaveDTO {
//        private Integer id; //
//        private Integer cartId;
//        //private User user; // 사용자 ID만 포함
//        private Product product; // 상품 ID만 포함
//        private String img; //상품이미지
//        private String name; //상품명
//        private Integer price; //상품가격
//        private Integer sum; //구매수량
//        //private Integer totalQty; //총 결제 금액
//        //private Boolean status;
//        private OrderStatus status;
//
//        public OrderSaveDTO(Order order) {
//            this.id = order.getId();
//            this.cartId = order.getCart().getId();
//            //this.user = order.getUser();
//            this.product = order.getProduct();
//            this.img = order.getProduct().getImg();
//            this.name = order.getProduct().getName();
//            this.price = order.getProduct().getPrice();
//            this.sum = order.getSum();
//            //this.totalQty = order.getTotalQty();
//            this.status = order.getStatus();
//        }
//    }


}