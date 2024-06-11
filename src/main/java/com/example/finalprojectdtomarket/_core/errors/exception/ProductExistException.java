package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class ProductExistException extends RuntimeException {

    public ProductExistException() {
        super(ExceptionEnum.PRODUCT_EXIST_EXCEPTION.getValue());
    }
}
