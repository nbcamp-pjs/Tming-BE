package com.spring.tming.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateReq {
    private Long userId;
    private Long commentId;
    private String content;

    @Builder
    private CommentUpdateReq(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
