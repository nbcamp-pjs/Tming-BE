package com.spring.tming.domain.emailVerify.dto.response;

import lombok.*;

@Getter
public class EmailAuthRes {

    private boolean success; // 인증 성공 여부

    public EmailAuthRes(boolean success) {
        this.success = success;
    }
}
