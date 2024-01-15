package com.spring.tming.domain.post.dto.response;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJobLimitRes {
    private Job job;
    private Integer headcount;

    @Builder
    private PostJobLimitRes(Job job, Integer headcount) {
        this.job = job;
        this.headcount = headcount;
    }
}
