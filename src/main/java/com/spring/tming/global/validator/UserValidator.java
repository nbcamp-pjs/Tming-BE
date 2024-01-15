package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.DUPLICATED_USERNAME_OR_EMAIL;
import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_USER;

import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.exception.GlobalException;

public class UserValidator {
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
}
