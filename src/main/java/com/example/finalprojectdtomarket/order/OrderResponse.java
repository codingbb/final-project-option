package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.product.Product;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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
        private String address;
        private String personName;    //유저이름
        private String img;

        // 주문번호
        private String orderNumb;
        // 주문 취소가 잘 안보여서 색 변경
        private String classChange;


        public ListDTO(OrderItem orderItem) {
            this.orderId = orderItem.getOrder().getId();
            this.pName = orderItem.getProduct().getName(); // lazy 로딩
            this.sum = orderItem.getOrder().getSum();
            this.status = orderItem.getOrder().getStatus();
            this.orderQty = orderItem.getOrderQty();
            this.productId = orderItem.getProduct().getId();
            this.createdAt = orderItem.getOrder().getCreatedAt().toLocalDateTime().toLocalDate();
            this.img = orderItem.getProduct().getImg();
            this.address = orderItem.getOrder().getAddress();
            this.personName = orderItem.getOrder().getUser().getPersonName();
            this.orderNumb = orderItem.getOrder().getOrderNumb();

            classChange();
        }

        // Lombok에서 생성된 getStatus() 메서드를 오버라이드
        public String getStatus() {
            if (OrderStatus.ORDER_COMPLETE.equals(status)) {
                return "주문완료";
            }
            if (OrderStatus.ORDER_CANCEL.equals(status)) {
                return "주문취소";
            }
            return "다시 확인하세요";
        }

        //색 변경용
        public String classChange() {
            if (OrderStatus.ORDER_COMPLETE.equals(status)) {
                return this.classChange = "color: green";
            } else {
                return this.classChange = "color: red";
            }
        }

    }

    @Data
    public static class ListDTOV2 {
        private Integer orderId;    //order PK
        private String pName;
        private Integer sum;     //order
        private OrderStatus status; //이거 타입 보류
        private Integer orderQty;
        private Integer productId;
        private Integer price;
        private Integer qty;
        private String img;
        private String classChange;

        // 재고처리 - 재고있음, 재고없음
        private String stock;

        public String stockExist() {
            if (orderQty > qty) {
                return "재고없음";
            } else {
                return "재고있음";
            }

        }

        public ListDTOV2(OrderItem orderItem) {
            this.orderId = orderItem.getOrder().getId();
            this.pName = orderItem.getProduct().getName(); // lazy 로딩
            this.sum = orderItem.getProduct().getPrice() * orderItem.getOrderQty();
            this.status = orderItem.getOrder().getStatus();
            this.orderQty = orderItem.getOrderQty();
            this.productId = orderItem.getProduct().getId();
            this.price = orderItem.getProduct().getPrice();
            this.qty = orderItem.getProduct().getQty();
            this.img = orderItem.getProduct().getImg();
            classChange();
            this.stock = stockExist();
        }

        // Lombok에서 생성된 getStatus() 메서드를 오버라이드
        public String getStatus() {
            if (OrderStatus.ORDER_COMPLETE.equals(status)) {
                return "주문완료";
            }
            if (OrderStatus.ORDER_CANCEL.equals(status)) {
                return "주문취소";
            }
            return "다시 확인하세요";
        }

        //색 변경용
        public String classChange() {
            if (OrderStatus.ORDER_COMPLETE.equals(status)) {
                return this.classChange = "color: green";
            } else {
                return this.classChange = "color: red";
            }
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