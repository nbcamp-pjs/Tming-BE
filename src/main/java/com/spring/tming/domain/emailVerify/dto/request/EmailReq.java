package com.spring.tming.domain.emailVerify.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailReq {
    private String email;

    @Builder
    private EmailReq(String email) {
        this.email = email;
    }
}
// 사용자가 인증번호를 받을 이메일을 입력하기 위한 DTO
