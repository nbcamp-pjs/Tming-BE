package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_COMMENT;

import com.spring.tming.domain.comment.entity.Comment;
import com.spring.tming.global.exception.GlobalException;

public class CommentValidator {
    public static void validate(Comment comment) {
        if (isNullComment(comment)) {
            throw new GlobalException(NOT_FOUND_COMMENT);
        }
    }

    private static boolean isNullComment(Comment comment) {
        return comment == null;
    }
}
