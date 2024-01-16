package com.spring.tming.domain.user.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingGetResList {
    private List<FollowingGetRes> followings;
    private int total;

    @Builder
    private FollowingGetResList(List<FollowingGetRes> followings, int total) {
        this.followings = followings;
        this.total = total;
    }
}
