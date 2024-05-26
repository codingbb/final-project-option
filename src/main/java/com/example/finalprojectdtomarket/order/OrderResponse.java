package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.product.Product;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderResponse {



    // List<TestDTO> - 2크기
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

        //위에게 한 번 돌때, 밑에는 여러번 (댓글처럼!)
        private List<ItemDTO> items = new ArrayList<>(); // 2

        public ListDTO(OrderItem orderItem, List<OrderItem> items) {
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
            this.classChange = classChange();

            this.items = items.stream().filter(item -> item.getOrder().getId().equals(this.orderId))
                    .map(item -> new ItemDTO(item)).toList();

        }


        @Data
        public class ItemDTO {
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

            public ItemDTO(OrderItem orderItem) {
                this.orderId = orderItem.getOrder().getId();
                this.pName = orderItem.getProduct().getName(); // lazy 로딩
                this.sum = orderItem.getProduct().getPrice() * orderItem.getOrderQty();
                this.status = orderItem.getOrder().getStatus();
                this.orderQty = orderItem.getOrderQty();
                this.productId = orderItem.getProduct().getId();
                this.price = orderItem.getProduct().getPrice();
                this.qty = orderItem.getProduct().getQty();
                this.img = orderItem.getProduct().getImg();
                this.classChange = classChange();
            }

            public String getStatus() {
                if (OrderStatus.ORDER_COMPLETE.equals(status)) {
                    return "주문완료";
                }
                if (OrderStatus.ORDER_CANCEL.equals(status)) {
                    return "주문취소";
                }
                return "다시 확인하세요";
            }

        }

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



}