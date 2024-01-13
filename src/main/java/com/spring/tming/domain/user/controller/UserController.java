package com.spring.tming.domain.user.controller;

import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.service.UserService;
import com.spring.tming.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public RestResponse<Void> signup(@RequestBody SignupReq signupReq) {
        return RestResponse.success(userService.signup(signupReq));
    }
}
