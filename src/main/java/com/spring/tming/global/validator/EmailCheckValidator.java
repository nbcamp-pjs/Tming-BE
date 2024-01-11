package com.spring.tming.global.validator;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCheckValidator {
    public static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new GlobalException(ResultCode.EMPTY_EMAIL);
        }

        // 이메일 유효성 검사 정규표현식
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new GlobalException(ResultCode.INVALID_EMAIL);
        }
    }
}
