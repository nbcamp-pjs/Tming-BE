package com.spring.tming.domain.emailVerify.controller;

import com.spring.tming.domain.emailVerify.dto.EmailReq;
import com.spring.tming.domain.emailVerify.service.EmailSendService;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.validator.EmailCheckValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailSendService emailSendService;

    public EmailController(EmailSendService emailSendService) {
        this.emailSendService = emailSendService;
    }

    @PostMapping("/mailSend")
    public String mailSend(@RequestBody EmailReq emailReq) {
        try {
            EmailCheckValidator.validateEmail(emailReq.getEmail());
            System.out.println("이메일 인증 이메일: " + emailReq.getEmail());
            return emailSendService.joinEmail(emailReq.getEmail());
        } catch (GlobalException e) {
            // 예외 처리를 통해 유효성 검사 결과 반환
            return e.getResultCode().getMessage();
        }
    }
}
