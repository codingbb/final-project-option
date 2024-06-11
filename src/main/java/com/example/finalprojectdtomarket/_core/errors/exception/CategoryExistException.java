package com.example.finalprojectdtomarket._core.errors.exception;

import com.example.finalprojectdtomarket._core.errors.ExceptionEnum;

public class CategoryExistException extends RuntimeException {

    public CategoryExistException() {
        super(ExceptionEnum.CATEGORY_EXIST_EXCEPTION.getValue());
    }
}
