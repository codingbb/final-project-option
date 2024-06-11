package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class UkException extends RuntimeException {

    public UkException() {
        super(ExceptionEnum.UK_EXCEPTION.getValue());
    }
}
