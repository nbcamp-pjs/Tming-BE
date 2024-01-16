package com.spring.tming.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGetRes {
    private Long userId;
    private String email;
    private String username;
    private String job;
    private String introduce;
    private int following;
    private int follower;
    private String profileImageUrl;

    @Builder
    private UserGetRes(
            Long userId,
            String email,
            String username,
            String job,
            String introduce,
            int following,
            int follower,
            String profileImageUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.job = job;
        this.introduce = introduce;
        this.following = following;
        this.follower = follower;
        this.profileImageUrl = profileImageUrl;
    }
}
