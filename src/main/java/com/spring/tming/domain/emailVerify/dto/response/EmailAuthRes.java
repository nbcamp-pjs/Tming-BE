package com.spring.tming.domain.emailVerify.dto.response;

import lombok.*;

@Getter
@Builder
public class EmailAuthRes {

    private final boolean success; // 인증 성공 여부
}
