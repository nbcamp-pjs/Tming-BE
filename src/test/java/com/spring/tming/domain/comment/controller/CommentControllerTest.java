package com.spring.tming.domain.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.comment.dto.request.CommentDeleteReq;
import com.spring.tming.domain.comment.dto.request.CommentSaveReq;
import com.spring.tming.domain.comment.dto.request.CommentUpdateReq;
import com.spring.tming.domain.comment.dto.response.CommentDeleteRes;
import com.spring.tming.domain.comment.dto.response.CommentGetRes;
import com.spring.tming.domain.comment.dto.response.CommentGetResList;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.dto.response.CommentUpdateRes;
import com.spring.tming.domain.comment.service.CommentService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest extends BaseMvcTest {
    @MockBean private CommentService commentService;

    @Test
    @DisplayName("comment 저장 테스트")
    void comment_저장() throws Exception {
        Long commentId = 1L;
        Long postId = 1L;
        String content = "content";
        CommentSaveReq commentSaveReq =
                CommentSaveReq.builder().postId(postId).content(content).build();
        CommentSaveRes commentSaveRes = CommentSaveRes.builder().commentId(commentId).build();
        when(commentService.saveComment(any())).thenReturn(commentSaveRes);
        this.mockMvc
                .perform(
                        post("/v1/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentSaveReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("comment 수정 테스트")
    void comment_수정() throws Exception {
        Long commentId = 1L;
        String content = "content";
        CommentUpdateReq commentUpdateReq =
                CommentUpdateReq.builder().commentId(commentId).content(content).build();
        CommentUpdateRes commentUpdateRes = new CommentUpdateRes();
        when(commentService.updateComment(any())).thenReturn(commentUpdateRes);
        this.mockMvc
                .perform(
                        patch("/v1/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentUpdateReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("comment 삭제 테스트")
    void comment_삭제() throws Exception {
        Long commentId = 1L;
        CommentDeleteReq commentDeleteReq = CommentDeleteReq.builder().commentId(commentId).build();
        CommentDeleteRes commentDeleteRes = new CommentDeleteRes();
        when(commentService.deleteComment(any())).thenReturn(commentDeleteRes);
        this.mockMvc
                .perform(
                        delete("/v1/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentDeleteReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("postId로 comments 조회 테스트")
    void postId_comments_조회() throws Exception {
        Long postId = 1L;
        Long commentId = 1L;
        String username = "ysys";
        String content = "content";
        String createTimestamp = "2024.01.01 01:01:11";
        CommentGetRes commentGetRes =
                CommentGetRes.builder()
                        .commentId(commentId)
                        .username(username)
                        .content(content)
                        .createTimestamp(createTimestamp)
                        .build();
        CommentGetResList commentGetResList =
                CommentGetResList.builder().comments(List.of(commentGetRes)).total(1).build();
        when(commentService.getComments(any())).thenReturn(commentGetResList);
        this.mockMvc.perform(get("/v1/comments/" + postId)).andDo(print()).andExpect(status().isOk());
    }
}
