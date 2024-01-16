package com.spring.tming.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingGetRes {
    private Long userId;
    private String username;
    private String profileImageUrl;

    @Builder
    private FollowingGetRes(Long userId, String username, String profileImageUrl) {
        this.userId = userId;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
    }
}
