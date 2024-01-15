package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.ALREADY_LIKED_POST;
import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_POST;
import static com.spring.tming.global.meta.ResultCode.NOT_YET_LIKED_POST;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostLike;
import com.spring.tming.global.exception.GlobalException;

public class PostValidator {

    public static void checkIsNullPost(Post post) {
        if (isNullPost(post)) {
            throw new GlobalException(NOT_FOUND_POST);
        }
    }

    public static void checkAlreadyLiked(PostLike postLike) {
        if (isExistPostLike(postLike)) {
            throw new GlobalException(ALREADY_LIKED_POST);
        }
    }

    public static void checkNotYetLiked(PostLike postLike) {
        if (!isExistPostLike(postLike)) {
            throw new GlobalException(NOT_YET_LIKED_POST);
        }
    }

    private static boolean isExistPostLike(PostLike postLike) {
        return postLike != null;
    }

    private static boolean isNullPost(Post post) {
        return post == null;
    }
}
