package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_POST;
import static com.spring.tming.global.meta.ResultCode.POST_INVALID_AUTHORIZATION;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.exception.GlobalException;
import java.util.Objects;

public class PostValidator {

    public static void checkIsNullPost(Post post) {
        if (isNullPost(post)) {
            throw new GlobalException(NOT_FOUND_POST);
        }
    }

    private static boolean isNullPost(Post post) {
        return post == null;
    }

	public static void checkIsPostUser(Post post, User user) {
		if (isUserIdEqual(post, user)) {
			throw new GlobalException(POST_INVALID_AUTHORIZATION);
		}
	}

	private static boolean isUserIdEqual(Post post, User user) {
		return !Objects.equals(post.getUser().getUserId(), user.getUserId());
	}
}
