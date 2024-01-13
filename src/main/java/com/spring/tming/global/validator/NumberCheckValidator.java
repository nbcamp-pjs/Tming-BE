package com.spring.tming.global.validator;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;

public class NumberCheckValidator {
    private static final String NUMBER_REGEX = "^[0-9]+$";
    // 무거운 예외처리 대신 인증번호 유효성 검사를 위한 정규식 도입
    public static void validateNumber(String number) {
        if (isNumberEmpty(number)) {
            throw new GlobalException(ResultCode.EMPTY_NUMBER);
        }
        // 숫자 형식 검증 로직 추가
        if (!isNumberValid(number)) {
            throw new GlobalException(ResultCode.INVALID_NUMBER);
        }
    }

    private static boolean isNumberEmpty(String number) {
        return number == null || number.isEmpty();
    }

    private static boolean isNumberValid(String number){
        return  number.matches(NUMBER_REGEX);
    }
}
