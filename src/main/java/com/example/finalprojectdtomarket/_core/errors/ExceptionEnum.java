package com.example.finalprojectdtomarket._core.errors;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    PRODUCT_NAME_SAME_EXCEPTION("상품명 중복입니다"),
    UK_EXCEPTION("회원 아이디 중복입니다"),
    LOGIN_FAIL_EXCEPTION("아이디 혹은 비번이 틀렸습니다");

    private String value;

    ExceptionEnum(String value) {
        this.value = value;
    }
}
