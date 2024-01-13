package com.spring.tming.domain.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.comment.dto.request.CommentSaveReq;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.service.CommentService;
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
}
