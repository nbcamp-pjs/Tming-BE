package com.spring.tming.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveReq {
    private Long userId;
    private Long postId;
    private String content;

    @Builder
    private CommentSaveReq(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }
}
