package com.spring.tming.domain.applicant.dto.request;

import com.spring.tming.global.meta.Job;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantSaveReq {
    private Long userId;
    private Long postId;
    private Job job;

    @Builder
    private ApplicantSaveReq(Long userId, Long postId, Job job) {
        this.userId = userId;
        this.postId = postId;
        this.job = job;
    }
}
