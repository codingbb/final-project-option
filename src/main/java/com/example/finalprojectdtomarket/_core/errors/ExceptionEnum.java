package com.example.finalprojectdtomarket._core.errors;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    PRODUCT_NAME_SAME_EXCEPTION("상품명 중복입니다"),
    UK_EXCEPTION("중복된 아이디 입니다"),
    LOGIN_FAIL_EXCEPTION("아이디 혹은 비밀번호가 일치하지 않습니다"),
    PRODUCT_EXIST_EXCEPTION("상품이 존재하지 않습니다"),
    USER_EXIST_EXCEPTION("존재하지 않는 사용자입니다"),
    OUT_OF_STOCK_EXCEPTION("재고가 부족합니다"),
    CART_NOT_FOUND_EXCEPTION("해당 장바구니 항목을 찾을 수 없습니다"),
    CATEGORY_EXIST_EXCEPTION("카테고리가 존재하지 않습니다");

    private String value;

    ExceptionEnum(String value) {
        this.value = value;
    }
}
