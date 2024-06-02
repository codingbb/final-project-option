package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.product.Product;
import lombok.Data;

public class CartResponse {

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

        private Boolean status;

        public ListDTO(Cart cart) {
            this.id = cart.getId();
            this.orderQty = cart.getOrderQty();
            //TODO: 이미지는 product에서 값 넘겨받은 후 하자
//            this.img = cart.get.getImg();
            this.optionId = cart.getOption().getId();
            this.pName = cart.getOption().getOptionName();
            this.price = cart.getOption().getPrice();
            this.status = cart.getStatus();
            this.sum = cart.getOrderQty() * cart.getOption().getPrice();
        }
    }


}
