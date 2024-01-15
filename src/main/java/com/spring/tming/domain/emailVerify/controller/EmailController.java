package com.spring.tming.domain.emailVerify.controller;

import com.spring.tming.domain.emailVerify.dto.request.EmailCheckReq;
import com.spring.tming.domain.emailVerify.dto.request.EmailReq;
import com.spring.tming.domain.emailVerify.dto.response.EmailRes;
import com.spring.tming.domain.emailVerify.service.EmailSendService;
import com.spring.tming.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class EmailController {

    private final EmailSendService emailSendService;

    @PostMapping("/email")
    public RestResponse<EmailRes> mailPass(@RequestBody EmailReq emailReq) {
        EmailRes emailRes = emailSendService.trialEmail(emailReq);
        return RestResponse.success(emailRes);
    }

    // 이메일로 받은 인증번호를 확인하는 엔드포인트
    @PostMapping("/email/verify")
    public RestResponse<EmailRes> verifyEmail(@RequestBody EmailCheckReq emailCheckReq) {
        emailSendService.verifyAuthNumber(emailCheckReq);
        return RestResponse.success(new EmailRes());
    }
}
