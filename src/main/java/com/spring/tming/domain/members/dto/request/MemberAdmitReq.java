package com.spring.tming.domain.members.dto.request;

import com.spring.tming.global.meta.Job;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAdmitReq {
    private Long userId;
    private Long postId;
    private Job job;

    @Builder
    private MemberAdmitReq(Long userId, Long postId, Job job) {
        this.userId = userId;
        this.postId = postId;
        this.job = job;
    }
}
