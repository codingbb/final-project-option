package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
        super(ExceptionEnum.CART_NOT_FOUND_EXCEPTION.getValue());
    }
}
