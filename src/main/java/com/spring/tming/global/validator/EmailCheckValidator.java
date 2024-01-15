package com.spring.tming.global.validator;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCheckValidator {
    // 이메일 유효성 검사 정규표현식 상수화
    private static final String emailRegex = "^[a-zA-Z0-9+-.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public static void validateEmail(String email) {
        validateEmailNotEmpty(email);
        validateEmailFormat(email);
    }

    private static void validateEmailNotEmpty(String email) {
        if (isEmailEmpty(email)) {
            throw new GlobalException(ResultCode.EMPTY_EMAIL);
        }
    }

    private static boolean isEmailEmpty(String email) {
        return email == null || email.isEmpty();
    }

    private static void validateEmailFormat(String email) {
        if (!isEmailValid(email)) {
            throw new GlobalException(ResultCode.INVALID_EMAIL);
        }
    }

    private static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
