package com.spring.tming.domain.members.controller;

import com.spring.tming.domain.members.dto.request.EmitMemberReq;
import com.spring.tming.domain.members.dto.response.EmitMemberRes;
import com.spring.tming.domain.members.service.MemberService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping
    public RestResponse<EmitMemberRes> emitMember(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody EmitMemberReq request) {
        return RestResponse.success(memberService.emitMember(userDetails.getUser(), request));
    }
}
