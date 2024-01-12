package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_POST;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.global.exception.GlobalException;

public class PostValidator {

    public static void checkIsNullPost(Post post) {
        if (isNullPost(post)) {
            throw new GlobalException(NOT_FOUND_POST);
        }
    }

    private static boolean isNullPost(Post post) {
        return post == null;
    }
}
