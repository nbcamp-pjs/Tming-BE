package com.spring.tming.domain.emailVerify.controller;

import com.spring.tming.domain.emailVerify.dto.request.EmailReq;
import com.spring.tming.domain.emailVerify.dto.response.EmailRes;
import com.spring.tming.domain.emailVerify.service.EmailSendService;
import com.spring.tming.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailSendService emailSendService;

    @PostMapping("/send")
    public RestResponse<EmailRes> mailSend(@RequestBody EmailReq emailReq) {
        EmailRes result = emailSendService.trialEmail(emailReq.getEmail());
        return RestResponse.success(result);
    }
}
