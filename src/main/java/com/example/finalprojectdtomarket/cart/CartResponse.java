package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import lombok.Data;

public class CartResponse {

    @Data
    public static class StatusDTO {
        private CartStatus status;

        public StatusDTO(Cart cart) {
            this.status = cart.getStatus();
        }
    }



    @Data
    public static class ListDTO {
        private Integer id;     //cartId
        private Integer orderQty;       //구매수량

        private String img;     //product에 있음
        private Integer optionId;   //lazy로
        private String pName;       //상품명 (옵션에 있음)
        private Integer price;      //상품단가 (옵션에 있음)

        private Integer sum;        //가공하기
        private Integer indexNum;   //번호 이쁘게 가공용

        private CartStatus status;

        public ListDTO(Cart cart) {
            this.id = cart.getId();
            this.orderQty = cart.getOrderQty();
            // product나 option 어떻게 해야할지 몰라서 join fetch c.option o join fetch o.product p 로 들고옴
            this.img = cart.getOption().getProduct().getImages().get(0).getFilePath();
            this.optionId = cart.getOption().getId();
            this.pName = cart.getOption().getOptionName();
            this.price = cart.getOption().getPrice();
            this.status = cart.getStatus();
            this.sum = cart.getOrderQty() * cart.getOption().getPrice();
        }
    }


    @Data
    public static class saveFormList {
        private Integer id;          //cartId
        private Integer orderQty;       //구매수량

        private Integer optionId;
        private String pName;       //상품명
        private Integer price;      //상품단가

        private Integer sum;        //가공하기
        private Integer totalSum;
        private Integer indexNum;   //번호 이쁘게 가공용

        private CartStatus status;

        public saveFormList(Cart cart) {
            this.id = cart.getId();
            this.orderQty = cart.getOrderQty();
            this.optionId = cart.getOption().getId();
            this.pName = cart.getOption().getOptionName();
            this.price = cart.getOption().getPrice();
            this.status = cart.getStatus();
            this.sum = cart.getOrderQty() * cart.getOption().getPrice();
        }


    }
}
