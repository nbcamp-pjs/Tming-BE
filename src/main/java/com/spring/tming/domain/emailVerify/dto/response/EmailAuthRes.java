package com.spring.tming.domain.emailVerify.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthRes {

    private boolean success; // 인증 성공 여부

    @Builder
    private EmailAuthRes(boolean success) {
        this.success = success;
    }
}
