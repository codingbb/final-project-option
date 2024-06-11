package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class LoginFailException extends RuntimeException {

    public LoginFailException() {
        super(ExceptionEnum.LOGIN_FAIL_EXCEPTION.getValue());
    }
}
