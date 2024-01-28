package com.spring.tming.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMemberRes {
    private Long userId;
    private String profileImageUrl;
    private String username;

    @Builder
    private PostMemberRes(Long userId, String profileImageUrl, String username) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
    }
}
