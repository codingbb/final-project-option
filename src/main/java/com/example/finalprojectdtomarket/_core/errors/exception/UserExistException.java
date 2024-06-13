package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class UserExistException extends RuntimeException {

    public UserExistException() {
        super(ExceptionEnum.USER_EXIST_EXCEPTION.getValue());
    }
}
