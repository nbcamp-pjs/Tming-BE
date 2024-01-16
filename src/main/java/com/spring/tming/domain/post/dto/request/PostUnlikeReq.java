package com.spring.tming.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUnlikeReq {
    private Long userId;
    private Long postId;

    @Builder
    private PostUnlikeReq(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
