package com.spring.tming.domain.user.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowerGetResList {
    private List<FollowerGetRes> followers;
    private int total;

    @Builder
    private FollowerGetResList(List<FollowerGetRes> followers, int total) {
        this.followers = followers;
        this.total = total;
    }
}
