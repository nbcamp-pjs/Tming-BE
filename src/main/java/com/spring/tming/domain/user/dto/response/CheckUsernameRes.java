package com.spring.tming.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckUsernameRes {

    private String username;

    @Builder
    private CheckUsernameRes(String username) {
        this.username = username;
    }
}
