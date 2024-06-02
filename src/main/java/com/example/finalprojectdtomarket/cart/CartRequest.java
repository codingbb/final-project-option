package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.user.User;
import lombok.Data;

public class CartRequest {
    @Data
    public static class saveDTO {
        private Integer productId;
        private Integer optionId;
        private Integer orderQty;

        public Cart toEntity(User sessionUser, Option option, Boolean status) {
            return Cart.builder()
                    .option(option)
                    .orderQty(orderQty)
                    .status(status)
                    .user(sessionUser)
                    .build();

        }
    }

    @Data
    public static class UpdateDTO {
        private Integer cartId;
        private Integer optionId;
        private Integer orderQty;
        private Boolean status;


    }
}
