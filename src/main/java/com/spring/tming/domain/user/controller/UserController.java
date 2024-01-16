package com.spring.tming.domain.user.controller;

import com.spring.tming.domain.user.dto.request.FollowReq;
import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.dto.response.FollowRes;
import com.spring.tming.domain.user.dto.response.SignupRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.service.UserService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public RestResponse<SignupRes> signup(@RequestBody SignupReq signupReq) {
        return RestResponse.success(userService.signup(signupReq));
    }

    @GetMapping("/{userId}")
    public RestResponse<UserGetRes> getUserProfile(@PathVariable Long userId) {
        return RestResponse.success(userService.getUserProfile(userId));
    }

    @PostMapping("/follow")
    public RestResponse<FollowRes> followUser(
            @RequestBody FollowReq followReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        followReq.setFollowerId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.followUser(followReq));
    }
}
