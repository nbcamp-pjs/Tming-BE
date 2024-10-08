package com.spring.tming.domain.post.dto.request;

import com.spring.tming.global.meta.Skill;
import java.sql.Timestamp;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateReq {
    private Long postId;
    private String title;
    private String content;
    private Timestamp deadline;
    private List<PostJobLimitReq> jobLimits;
    private List<Skill> skills;
    private String imageUrl;

    @Builder
    private PostUpdateReq(
            Long postId,
            String title,
            String content,
            Timestamp deadline,
            List<PostJobLimitReq> jobLimits,
            List<Skill> skills,
            String imageUrl) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.jobLimits = jobLimits;
        this.skills = skills;
        this.imageUrl = imageUrl;
    }
}
