package com.spring.tming.domain.emailVerify.dto;

import com.spring.tming.global.validator.EmailCheckValidator;
import lombok.Getter;

@Getter
public class EmailReq {
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void validate() {
        EmailCheckValidator.validateEmail(email);
    }
}

// 사용자가 인증번호를 받을 이메일을 입력하기 위한 DTO
