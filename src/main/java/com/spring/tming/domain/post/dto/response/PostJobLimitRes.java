package com.spring.tming.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJobLimitRes {
    private String job;
    private Integer headcount;

    @Builder
    private PostJobLimitRes(String job, Integer headcount) {
        this.job = job;
        this.headcount = headcount;
    }
}
