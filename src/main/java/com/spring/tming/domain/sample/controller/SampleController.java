package com.spring.tming.domain.sample.controller;

import com.spring.tming.domain.sample.dto.request.SampleSaveReq;
import com.spring.tming.domain.sample.dto.response.SampleGetRes;
import com.spring.tming.domain.sample.dto.response.SampleGetResList;
import com.spring.tming.domain.sample.dto.response.SampleSaveRes;
import com.spring.tming.domain.sample.service.SampleService;
import com.spring.tming.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PostMapping
    public RestResponse<SampleSaveRes> saveSample(@RequestBody SampleSaveReq sampleSaveReq) {
        return RestResponse.success(sampleService.saveSample(sampleSaveReq));
    }

    @GetMapping
    public RestResponse<SampleGetResList> getAllSamples() {
        return RestResponse.success(sampleService.getAllSamples());
    }

    @GetMapping("/{sampleId}")
    public RestResponse<SampleGetRes> getSample(@PathVariable Long sampleId) {
        return RestResponse.success(sampleService.getSample(sampleId));
    }
}
