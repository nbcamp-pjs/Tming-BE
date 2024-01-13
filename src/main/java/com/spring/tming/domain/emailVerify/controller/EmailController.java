package com.spring.tming.domain.emailVerify.controller;

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
    public RestResponse<EmailRes> mailSend(@RequestParam String email) {
        EmailRes result = emailSendService.trialEmail(email);
        return RestResponse.success(result);
    }
}
