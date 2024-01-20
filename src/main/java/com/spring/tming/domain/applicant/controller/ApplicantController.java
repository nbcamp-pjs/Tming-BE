package com.spring.tming.domain.applicant.controller;

import com.spring.tming.domain.applicant.dto.request.ApplicantCancelReq;
import com.spring.tming.domain.applicant.dto.request.ApplicantSaveReq;
import com.spring.tming.domain.applicant.dto.response.ApplicantCancelRes;
import com.spring.tming.domain.applicant.dto.response.ApplicantGetResList;
import com.spring.tming.domain.applicant.dto.response.ApplicantSaveRes;
import com.spring.tming.domain.applicant.service.ApplicantService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping
    public RestResponse<ApplicantSaveRes> saveApplicantForPost(
            @RequestBody ApplicantSaveReq applicantSaveReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        applicantSaveReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(applicantService.saveApplicantForPost(applicantSaveReq));
    }

    @DeleteMapping
    public RestResponse<ApplicantCancelRes> cancelApplicant(
            @RequestBody ApplicantCancelReq applicantCancelReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        applicantCancelReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(applicantService.cancelApplicant(applicantCancelReq));
    }

    @GetMapping("/{postId}")
    public RestResponse<ApplicantGetResList> getApplicants(@PathVariable Long postId) {
        return RestResponse.success(applicantService.getApplicants(postId));
    }
}
