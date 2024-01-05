package com.spring.tming.global.exception;

import com.spring.tming.global.response.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public RestResponse<Void> handleException(GlobalException e) {
        return RestResponse.error(e.getResultCode());
    }
}
