package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.INVALID_TOKEN;

import com.spring.tming.global.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "token validator")
public class TokenValidator {
    public static void validate(String token) {
        if (isNullToken(token)) {
            throw new GlobalException(INVALID_TOKEN);
        }
    }

    public static void validate(boolean token) {
        if (!token) {
            throw new GlobalException(INVALID_TOKEN);
        }
    }

    private static boolean isNullToken(String token) {
        return token == null;
    }
}
