package com.spring.tming.domain.emailVerify.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailRes {

    private String email;
    private String message;

    @Builder
    private EmailRes(String email, String message) {
        this.email = email;
        this.message = message;
    }
}
