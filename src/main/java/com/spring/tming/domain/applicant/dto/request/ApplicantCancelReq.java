package com.spring.tming.domain.applicant.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantCancelReq {
    private Long userId;
    private Long postId;

    @Builder
    private ApplicantCancelReq(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
