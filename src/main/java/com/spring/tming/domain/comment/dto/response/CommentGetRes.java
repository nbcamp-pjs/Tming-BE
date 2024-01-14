package com.spring.tming.domain.comment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentGetRes {
    private Long commentId;
    private String username;
    private String content;
    private String createTimestamp;

    @Builder
    private CommentGetRes(Long commentId, String username, String content, String createTimestamp) {
        this.commentId = commentId;
        this.username = username;
        this.content = content;
        this.createTimestamp = createTimestamp;
    }
}
