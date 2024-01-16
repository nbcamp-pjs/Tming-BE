package com.spring.tming.domain.post.dto.request;

import com.spring.tming.global.meta.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostStatusUpdateReq {
    private Long postId;
    private Status status;

    @Builder
    private PostStatusUpdateReq(Long postId, Status status) {
        this.postId = postId;
        this.status = status;
    }
}
