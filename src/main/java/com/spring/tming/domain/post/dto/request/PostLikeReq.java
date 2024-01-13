package com.spring.tming.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeReq {
    private Long userId;
    private Long postId;

    @Builder
    private PostLikeReq(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
