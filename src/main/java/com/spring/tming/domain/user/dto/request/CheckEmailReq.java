package com.spring.tming.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckEmailReq {

    private String email;

    @Builder
    private CheckEmailReq(String email) {
        this.email = email;
    }
}
