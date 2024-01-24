package com.spring.tming.domain.members.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGetRes {
    private Long userId;
    private String username;
    private String job;
    private String profileImageUrl;

    @Builder
    private MemberGetRes(Long userId, String username, String job, String profileImageUrl) {
        this.userId = userId;
        this.username = username;
        this.job = job;
        this.profileImageUrl = profileImageUrl;
    }
}
