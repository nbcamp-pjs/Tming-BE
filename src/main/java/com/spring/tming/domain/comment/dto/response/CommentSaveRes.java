package com.spring.tming.domain.comment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveRes {
    private Long commentId;

    @Builder
    private CommentSaveRes(Long commentId) {
        this.commentId = commentId;
    }
}
