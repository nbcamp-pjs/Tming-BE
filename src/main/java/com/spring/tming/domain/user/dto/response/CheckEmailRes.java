package com.spring.tming.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckEmailRes {

    private String email;

    @Builder
    private CheckEmailRes(String email) {
        this.email = email;
    }
}
