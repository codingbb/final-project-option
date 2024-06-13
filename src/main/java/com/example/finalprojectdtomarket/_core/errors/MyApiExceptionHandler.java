package com.example.finalprojectdtomarket._core.errors;

import com.example.finalprojectdtomarket._core.errors.exception.OutOfStockException;
import com.example.finalprojectdtomarket._core.errors.exception.UkException;
import com.example.finalprojectdtomarket._core.util.ApiUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// RuntimeException이 터지면 해당 파일로 오류가 모인다
@RestControllerAdvice
public class MyApiExceptionHandler {
//    @ExceptionHandler(ApiException400.class)
//    public ResponseEntity<?> exApi400(ApiException400 e) {
////        System.out.println(e.getMessage() + "잘 들어오나요??");
//        ApiUtil<?> apiUtil = new ApiUtil<>(400, e.getMessage());
//        return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(UkException.class)
    public ResponseEntity<?> ukException(UkException e) {
//        System.out.println(e.getMessage() + "잘 들어오나요??");
        ApiUtil<?> apiUtil = new ApiUtil<>(false, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<?> outOfStockException(OutOfStockException e) {
//        System.out.println(e.getMessage() + "잘 들어오나요??");
        ApiUtil<?> apiUtil = new ApiUtil<>(false, e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST);
    }

}