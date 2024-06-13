package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super(ExceptionEnum.OUT_OF_STOCK_EXCEPTION.getValue());
    }
}
