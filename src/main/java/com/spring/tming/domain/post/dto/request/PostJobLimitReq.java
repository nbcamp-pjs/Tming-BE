package com.spring.tming.domain.post.dto.request;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJobLimitReq {
    private Job job;
    private Integer headcount;

    @Builder
    private PostJobLimitReq(Job job, Integer headcount) {
        this.job = job;
        this.headcount = headcount;
    }
}
