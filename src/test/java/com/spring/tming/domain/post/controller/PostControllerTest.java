package com.spring.tming.domain.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.post.dto.request.PostLikeReq;
import com.spring.tming.domain.post.dto.request.PostUnlikeReq;
import com.spring.tming.domain.post.dto.response.PostLikeRes;
import com.spring.tming.domain.post.dto.response.PostUnlikeRes;
import com.spring.tming.domain.post.service.PostLikeService;
import com.spring.tming.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {PostController.class})
class PostControllerTest extends BaseMvcTest {
    @MockBean private PostService postService;
    @MockBean private PostLikeService postLikeService;

    @Test
    @DisplayName("모집글 좋아요 테스트")
    void 모집글_좋아요() throws Exception {
        Long postId = 1L;
        PostLikeReq postLikeReq = PostLikeReq.builder().postId(postId).build();
        PostLikeRes postLikeRes = new PostLikeRes();
        when(postLikeService.likePost(any())).thenReturn(postLikeRes);
        this.mockMvc
                .perform(
                        post("/v1/posts/like")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postLikeReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모집글 좋아요 취소 테스트")
    void 모집글_좋아요_취소() throws Exception {
        Long postId = 1L;
        PostUnlikeReq postUnlikeReq = PostUnlikeReq.builder().postId(postId).build();
        PostUnlikeRes postUnlikeRes = new PostUnlikeRes();
        when(postLikeService.unlikePost(any())).thenReturn(postUnlikeRes);
        this.mockMvc
                .perform(
                        post("/v1/posts/unlike")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postUnlikeReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
