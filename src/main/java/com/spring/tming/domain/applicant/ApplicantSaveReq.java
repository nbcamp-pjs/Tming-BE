package com.spring.tming.domain.applicant;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantSaveReq {
    private Long postId;
    private Job job;
    private Long userId;

    @Builder
    private ApplicantSaveReq(Long postId, Job job, Long userId) {
        this.postId = postId;
        this.job = job;
        this.userId = userId;
    }
}
