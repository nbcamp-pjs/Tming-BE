package com.spring.tming.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDeleteReq {
    private Long commentId;
    private Long userId;

    @Builder
    private CommentDeleteReq(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }
}
