package com.spring.tming.domain.emailVerify.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailCheckReq {
    private String email;
    private String authNumber;

    @Builder
    private EmailCheckReq(String email, String authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}

// 사용자가 인증번호를 확인하고 인증번호를 입력하였을 때 그 값을 받아오는 DTO
