package com.spring.tming.domain.post.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAllReadRes {
    private Long postId;
    private String title;
    private String content;
    private String deadline;
    private Long visit;
    private Long like;
    private String imageUrl;
    private String status;
    private Long userId;
    private String username;
    private List<PostJobLimitRes> jobLimits;
    private String createTimestamp;

    @Builder
    private PostAllReadRes(
            Long postId,
            String title,
            String content,
            String deadline,
            Long visit,
            Long like,
            String imageUrl,
            String status,
            Long userId,
            String username,
            List<PostJobLimitRes> jobLimits,
            String createTimestamp) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.visit = visit;
        this.like = like;
        this.imageUrl = imageUrl;
        this.status = status;
        this.userId = userId;
        this.username = username;
        this.jobLimits = jobLimits;
        this.createTimestamp = createTimestamp;
    }
}
