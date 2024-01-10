package com.spring.tming.domain.post.dto.request;

import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Skill;
import java.sql.Timestamp;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateReq {
    private String title;
    private String content;
    private Timestamp deadline;
    private List<JobLimit> jobLimits;
    private List<Skill> skills;

    @Builder
    private PostCreateReq(
            String title,
            String content,
            Timestamp deadline,
            List<JobLimit> jobLimits,
            List<Skill> skills,
            String image) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.jobLimits = jobLimits;
        this.skills = skills;
    }
}
