package com.spring.tming.domain.comment.controller;

import com.spring.tming.domain.comment.dto.request.CommentDeleteReq;
import com.spring.tming.domain.comment.dto.request.CommentSaveReq;
import com.spring.tming.domain.comment.dto.request.CommentUpdateReq;
import com.spring.tming.domain.comment.dto.response.CommentDeleteRes;
import com.spring.tming.domain.comment.dto.response.CommentGetResList;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.dto.response.CommentUpdateRes;
import com.spring.tming.domain.comment.service.CommentService;
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

    @PatchMapping
    public RestResponse<CommentUpdateRes> updateComment(
            @RequestBody CommentUpdateReq commentUpdateReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentUpdateReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(commentService.updateComment(commentUpdateReq));
    }

    @DeleteMapping
    public RestResponse<CommentDeleteRes> deleteComment(
            @RequestBody CommentDeleteReq commentDeleteReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentDeleteReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(commentService.deleteComment(commentDeleteReq));
    }

    @GetMapping("/{postId}")
    public RestResponse<CommentGetResList> getComments(@PathVariable Long postId) {
        return RestResponse.success(commentService.getComments(postId));
    }
}
