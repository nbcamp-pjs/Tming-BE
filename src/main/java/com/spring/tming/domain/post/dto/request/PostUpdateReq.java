package com.spring.tming.domain.post.dto.request;

import com.spring.tming.domain.post.dto.PostJobLimitDto;
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
    private List<PostJobLimitDto> jobLimits;
    private List<Skill> skills;

    @Builder
    private PostUpdateReq(
            Long postId,
            String title,
            String content,
            Timestamp deadline,
            List<PostJobLimitDto> jobLimits,
            List<Skill> skills) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.jobLimits = jobLimits;
        this.skills = skills;
    }
}
