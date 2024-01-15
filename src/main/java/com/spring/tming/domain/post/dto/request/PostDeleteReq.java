package com.spring.tming.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDeleteReq {
    private Long postId;

    @Builder
    private PostDeleteReq(Long postId) {
        this.postId = postId;
    }
}
