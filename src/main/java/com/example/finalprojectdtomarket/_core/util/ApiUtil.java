package com.example.finalprojectdtomarket._core.util;

import lombok.Data;

@Data
public class ApiUtil<T> {

    private Integer status;
    private String msg;
    private T body;
    private Boolean b;

    public ApiUtil(T body) {
        this.status = 200;
        this.msg = "성공";
        this.body = body;
    }

    public ApiUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.body = null;
    }

    public ApiUtil(Boolean b, String msg) {
        this.status = b ? 200 : 400;    // true일 경우 200(성공), false일 경우 400(실패)
        this.msg = msg;
        this.body = null;
    }
}