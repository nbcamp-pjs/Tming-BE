package com.spring.tming.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutRes {

    private Long userId;

    @Builder
    private LogoutRes(Long userId) {
        this.userId = userId;
    }
}
