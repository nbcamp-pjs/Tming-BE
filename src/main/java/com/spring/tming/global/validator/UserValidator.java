package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.*;

import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.exception.GlobalException;

public class UserValidator {
    private static final String REGEX_USERNAME = "^[a-zA-Z0-9가-힣]{4,12}$";
    private static final String REGEX_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$";

    public static void validate(User user) {
        if (isNullUser(user)) {
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    private static boolean isNullUser(User user) {
        return user == null;
    }

    public static void duplicatedUser(User user) {
        if (!isNullUser(user)) {
            throw new GlobalException(DUPLICATED_USERNAME_OR_EMAIL);
        }
    }

    public static void validate(SignupReq req) {
        if (!isCorrectUsernameFormat(req.getUsername())) {
            throw new GlobalException(VALID_USERNAME);
        }
        if (!isCorrectPasswordFormat(req.getPassword())) {
            throw new GlobalException(VALID_PASSWORD);
        }
    }

    private static boolean isCorrectUsernameFormat(String username) {
        return username.matches(REGEX_USERNAME);
    }

    private static boolean isCorrectPasswordFormat(String password) {
        return password.matches(REGEX_PASSWORD);
    }
}
