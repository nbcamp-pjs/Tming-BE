package com.spring.tming.domain.applicant;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantId implements Serializable {

    private Long post;
    private Long user;

    @Builder
    private ApplicantId(Long postId, Long userId) {
        this.post = postId;
        this.user = userId;
    }
}
