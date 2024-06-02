package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.orderItem.OrderItemRequest;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderRequest {

    @Data
    public static class SaveDTO {
        private String address;

        private Integer totalSum; //총합계
        private OrderStatus status;
        private String orderNumb;

        private List<Integer> optionId;
        private List<String> pName;
        private List<Integer> price;  //계산된 가격

        //cart 부분
        private List<Integer> cartId;
        private List<Integer> orderQty;    //선택한 수량
        //?
        private List<Integer> orderId;

        //주문 번호 생성
        @Data
        public class MakeOrderNum {
            private String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            private Random random = new Random();

            public String makeNumb() {
                // 날짜를 YYMMDD 형식으로 포맷팅
                String dateFormat = DateTimeFormatter.ofPattern("yyMMdd").format(LocalDate.now());
                // 랜덤 문자열 생성
                StringBuilder randomPart = new StringBuilder(5);
                for (int i = 0; i < 5; i++) {
                    int index = random.nextInt(Alphabet.length());  //랜덤 인덱스 생성
                    char randomChar = Alphabet.charAt(index); // 랜덤 문자 선택
                    randomPart.append(randomChar);
                }

                //주문 번호 조합
                return dateFormat + randomPart;
            }

        }

        // user 들고 오는 부분
//        private Integer userId;
        public Order toOrderEntity(User user) {
            return Order.builder()
                    .user(user)
                    .orderNumb(new MakeOrderNum().makeNumb())
                    .address(address)
                    .sum(totalSum)
                    .status(status)
                    .build();
        }

        public OrderItem toOrderItemEntity(Order order, Option option, Integer quantity) {
            return OrderItem.builder()
                    .order(order)
                    .option(option)
                    .orderQty(quantity)
                    .build();

        }

    }

    @Data
    public static class CancelDTO {
        private Integer orderId;
        private Integer optionId;
        private Integer orderQty;
    }
}
