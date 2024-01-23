package com.spring.tming.domain.members.controller;

import com.spring.tming.domain.members.dto.request.emitMemberReq;
import com.spring.tming.domain.members.dto.response.emitMemberRes;
import com.spring.tming.domain.members.service.MemberService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/posts/{postId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping
    public RestResponse<emitMemberRes> emitMember(
            @PathVariable(name = "postId") Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody emitMemberReq request) {
        return RestResponse.success(memberService.emitMember(postId, userDetails.getUser(), request));
    }
}
