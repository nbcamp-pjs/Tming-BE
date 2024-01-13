package com.spring.tming.domain.post.controller;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostDeleteReq;
import com.spring.tming.domain.post.dto.request.PostUpdateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostDeleteRes;
import com.spring.tming.domain.post.dto.response.PostReadRes;
import com.spring.tming.domain.post.dto.response.PostUpdateRes;
import com.spring.tming.domain.post.service.PostService;
import com.spring.tming.global.response.RestResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public RestResponse<PostCreateRes> createPost(
            @RequestPart(name = "request") PostCreateReq postCreateReq,
            @RequestPart(name = "image", required = false) MultipartFile image)
            throws IOException { // 인증된 유저 정보 추가
        return RestResponse.success(postService.createPost(postCreateReq, image));
    }

    @PatchMapping
    public RestResponse<PostUpdateRes> updatePost(
            @RequestPart(name = "request") PostUpdateReq postUpdateReq,
            @RequestPart(name = "image", required = false) MultipartFile image)
            throws IOException {
        return RestResponse.success(postService.updatePost(postUpdateReq, image));
    }

    @DeleteMapping
    public RestResponse<PostDeleteRes> deletePost(@RequestBody PostDeleteReq postDeleteReq) {
        return RestResponse.success(postService.deletePost(postDeleteReq));
    }

    @GetMapping("/{postId}")
    public RestResponse<PostReadRes> readPost(@PathVariable Long postId) {
        return RestResponse.success(postService.readPost(postId));
    }
}
