package com.spring.tming.domain.comment.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentGetResList {
    private List<CommentGetRes> comments;
    private int total;

    @Builder
    private CommentGetResList(List<CommentGetRes> comments, int total) {
        this.comments = comments;
        this.total = total;
    }
}
