package com.spring.tming.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnfollowReq {
    private Long followerId;
    private Long followingId;

    @Builder
    private UnfollowReq(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
