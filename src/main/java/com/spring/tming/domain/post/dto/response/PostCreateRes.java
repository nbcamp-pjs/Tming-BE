package com.spring.tming.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRes {
    private Long postId;

    @Builder
    private PostCreateRes(Long postId) {
        this.postId = postId;
    }
}
