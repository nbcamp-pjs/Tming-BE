package com.spring.tming.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowReq {
    private Long followerId;
    private Long followingId;

    @Builder
    private FollowReq(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
