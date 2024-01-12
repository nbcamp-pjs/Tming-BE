package com.spring.tming.domain.comment.controller;

import com.spring.tming.domain.comment.dto.request.CommentSaveReq;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.service.CommentService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public RestResponse<CommentSaveRes> saveComment(
            @RequestBody CommentSaveReq commentSaveReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentSaveReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(commentService.saveComment(commentSaveReq));
    }
}
