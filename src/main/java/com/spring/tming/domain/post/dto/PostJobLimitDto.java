package com.spring.tming.domain.post.dto;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJobLimitDto {
    private Job job;
    private Integer headcount;

    @Builder
    private PostJobLimitDto(Job job, Integer headcount) {
        this.job = job;
        this.headcount = headcount;
    }
}
