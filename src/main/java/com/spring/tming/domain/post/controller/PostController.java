package com.spring.tming.domain.post.controller;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostDeleteReq;
import com.spring.tming.domain.post.dto.request.PostUpdateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostDeleteRes;
import com.spring.tming.domain.post.dto.response.PostReadRes;
import com.spring.tming.domain.post.dto.response.PostReadResList;
import com.spring.tming.domain.post.dto.response.PostUpdateRes;
import com.spring.tming.domain.post.entity.Job;
import com.spring.tming.domain.post.entity.Skill;
import com.spring.tming.domain.post.enums.Type;
import com.spring.tming.domain.post.service.PostService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestPart(name = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws IOException {
        return RestResponse.success(postService.createPost(postCreateReq, image, userDetails.getUser()));
    }

    @PatchMapping
    public RestResponse<PostUpdateRes> updatePost(
            @RequestPart(name = "request") PostUpdateReq postUpdateReq,
            @RequestPart(name = "image", required = false) MultipartFile image,
        @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws IOException {
        return RestResponse.success(postService.updatePost(postUpdateReq, image, userDetails.getUser()));
    }

    @DeleteMapping
    public RestResponse<PostDeleteRes> deletePost(@RequestBody PostDeleteReq postDeleteReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(postService.deletePost(postDeleteReq, userDetails.getUser()));
    }

    @GetMapping("/{postId}")
    public RestResponse<PostReadRes> readPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(postService.readPost(postId));
    }

    @GetMapping
    public RestResponse<PostReadResList> readPostList(@RequestParam(name = "type", defaultValue = "ALL") Type type, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(postService.readPostList(type, userDetails.getUser()));
    }

    @GetMapping
    public RestResponse<PostReadResList> readPostListBySkill(@RequestParam(name = "skill") Skill skill, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(postService.readPostListBySkill(skill, userDetails.getUser()));
    }

    @GetMapping
    public RestResponse<PostReadResList> readPostListByJob(@RequestParam(name = "job") Job job, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return RestResponse.success(postService.readPostListByJob(job, userDetails.getUser()));
    }
}
