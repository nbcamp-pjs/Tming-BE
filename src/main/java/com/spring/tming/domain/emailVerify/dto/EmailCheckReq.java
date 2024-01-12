package com.spring.tming.domain.emailVerify.dto;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.validator.EmailCheckValidator;
import java.util.ArrayList;
import java.util.List;

// 사용자가 인증번호를 확인하고 인증번호를 입력하였을 때 받아오는 DTO
public class EmailCheckReq {
    private String email;
    private String authNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (isEmailEmpty(email)) {
            errors.add("이메일을 입력해 주세요");
        } else {
            try {
                EmailCheckValidator.validateEmail(email);
            } catch (GlobalException e) {
                errors.add(e.getResultCode().getMessage());
            }
        }

        if (isAuthNumberEmpty(authNumber)) {
            errors.add("인증 번호를 입력해 주세요");
        }

        return errors;
    }

    private boolean isEmailEmpty(String email) {
        return email == null || email.isEmpty();
    }

    private boolean isAuthNumberEmpty(String authNumber) {
        return authNumber == null || authNumber.isEmpty();
    }
}
