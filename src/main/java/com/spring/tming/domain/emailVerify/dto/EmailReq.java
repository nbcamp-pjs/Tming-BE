package com.spring.tming.domain.emailVerify.dto;

import com.spring.tming.global.validator.EmailCheckValidator;
import lombok.Getter;

// 사용자의 이메일을 받기 위한 DTO
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
