package com.spring.tming.domain.user.controller;

import com.spring.tming.domain.user.dto.request.FollowReq;
import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.dto.request.UnfollowReq;
import com.spring.tming.domain.user.dto.request.UserUpdateReq;
import com.spring.tming.domain.user.dto.response.FollowRes;
import com.spring.tming.domain.user.dto.response.FollowerGetResList;
import com.spring.tming.domain.user.dto.response.FollowingGetResList;
import com.spring.tming.domain.user.dto.response.SignupRes;
import com.spring.tming.domain.user.dto.response.UnfollowRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.dto.response.UserUpdateRes;
import com.spring.tming.domain.user.service.UserService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @DeleteMapping("/unfollow")
    public RestResponse<UnfollowRes> unfollowUser(
            @RequestBody UnfollowReq unfollowReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        unfollowReq.setFollowerId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.unfollowUser(unfollowReq));
    }

    @GetMapping("/follower/{userId}")
    public RestResponse<FollowerGetResList> getFollowers(@PathVariable Long userId) {
        return RestResponse.success(userService.getFollowers(userId));
    }

    @GetMapping("/following/{userId}")
    public RestResponse<FollowingGetResList> getFollowings(@PathVariable Long userId) {
        return RestResponse.success(userService.getFollowings(userId));
    }

    @PatchMapping
    public RestResponse<UserUpdateRes> updateUser(
            @RequestPart UserUpdateReq userUpdateReq,
            @RequestPart MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userUpdateReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(userService.updateUser(userUpdateReq, multipartFile));
    }
}
