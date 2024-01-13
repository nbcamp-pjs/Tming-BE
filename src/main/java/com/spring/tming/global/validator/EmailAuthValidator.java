package com.spring.tming.global.validator;

import com.spring.tming.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;

public class EmailAuthValidator {
    private String email;
    private String authNumber;

    public EmailAuthValidator(String email, String authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();

        // 이메일 검증
        try {
            EmailCheckValidator.validateEmail(email);
        } catch (GlobalException e) {
            errors.add(e.getResultCode().getMessage());
        }

        // 인증 번호 검증
        try {
            NumberCheckValidator.validateNumber(authNumber);
        } catch (GlobalException e) {
            errors.add(e.getResultCode().getMessage());
        }

        return errors;
    }
}
