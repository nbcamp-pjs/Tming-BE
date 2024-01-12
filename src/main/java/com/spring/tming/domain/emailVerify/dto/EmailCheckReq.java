package com.spring.tming.domain.emailVerify.dto;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.validator.EmailCheckValidator;
import com.spring.tming.global.validator.NumberCheckValidator;
import java.util.ArrayList;
import java.util.List;
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

// 사용자가 인증번호를 확인하고 인증번호를 입력하였을 때 그 값을 받아오는 DTO
