package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.ALREADY_LIKED_POST;
import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_POST;
import static com.spring.tming.global.meta.ResultCode.NOT_YET_LIKED_POST;
import static com.spring.tming.global.meta.ResultCode.POST_INVALID_AUTHORIZATION;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostLike;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.exception.GlobalException;
import java.util.Objects;

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

    public static void checkIsPostUser(Post post, User user) {
        if (!isUserIdEqual(post, user)) {
            throw new GlobalException(POST_INVALID_AUTHORIZATION);
        }
    }

    private static boolean isUserIdEqual(Post post, User user) {
        return Objects.equals(post.getUser().getUserId(), user.getUserId());
    }
}
