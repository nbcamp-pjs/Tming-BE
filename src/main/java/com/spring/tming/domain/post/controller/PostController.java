package com.spring.tming.domain.post.controller;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.service.PostService;
import com.spring.tming.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public RestResponse<PostCreateRes> createPost(@RequestBody PostCreateReq postCreateReq) { // 인증된 유저 정보 추가
        return RestResponse.success(postService.createPost(postCreateReq));
    }
}
