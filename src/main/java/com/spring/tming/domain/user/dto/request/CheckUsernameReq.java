package com.spring.tming.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckUsernameReq {

    private String username;

    @Builder
    private CheckUsernameReq(String username) {
        this.username = username;
    }
}
