package com.spring.tming.global.validator;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;

public class NumberCheckValidator {
    public static void validateNumber(String number) {
        if (isNumberEmpty(number)) {
            throw new GlobalException(ResultCode.EMPTY_NUMBER);
        }
        // 숫자 형식 검증 로직 추가
        if (!isNumberValid(number)) {
            throw new GlobalException(ResultCode.INVALID_NUMBER);
        }
    }

    // EmailCheckValidator 클래스의 validateEmail 메소드를 재사용
    public static void validateEmail(String email) {
        EmailCheckValidator.validateEmail(email);
    }

    private static boolean isNumberEmpty(String number) {
        return number == null || number.isEmpty();
    }

    private static boolean isNumberValid(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
