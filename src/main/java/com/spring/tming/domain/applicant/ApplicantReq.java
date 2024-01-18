package com.spring.tming.domain.applicant;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantReq {
    private Long postId;
    private Job job;

    @Builder
    private ApplicantReq(Long postId, Job job) {
        this.postId = postId;
        this.job = job;
    }
}
