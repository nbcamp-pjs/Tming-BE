package com.spring.tming.domain.post.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostJobLimitReq;
import com.spring.tming.domain.post.dto.request.PostLikeReq;
import com.spring.tming.domain.post.dto.request.PostUnlikeReq;
import com.spring.tming.domain.post.dto.request.PostUpdateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostLikeRes;
import com.spring.tming.domain.post.dto.response.PostUnlikeRes;
import com.spring.tming.domain.post.dto.response.PostUpdateRes;
import com.spring.tming.domain.post.service.PostLikeService;
import com.spring.tming.domain.post.service.PostService;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import com.spring.tming.global.security.UserDetailsImpl;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

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
                        delete("/v1/posts/unlike")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postUnlikeReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모집글 생성 테스트")
    void createPostTest() throws Exception {
        // given
        Long postId = 1L;
        String title = "title";
        String content = "content";
        Timestamp deadline = Timestamp.valueOf(LocalDateTime.now());
        List<PostJobLimitReq> jobLimits =
                List.of(PostJobLimitReq.builder().headcount(1).job(Job.BACKEND).build());
        List<Skill> skills = List.of(Skill.JAVA, Skill.SPRING);
        PostCreateReq postCreateReq =
                PostCreateReq.builder()
                        .title(title)
                        .content(content)
                        .deadline(deadline)
                        .jobLimits(jobLimits)
                        .skills(skills)
                        .build();

        Resource resource = new ClassPathResource("images/sparta.png");
        byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        MockMultipartFile imageFile = new MockMultipartFile(
            "image",
            "sparta.png",
            "image/png",
            imageBytes);

        UserDetailsImpl userDetails = new UserDetailsImpl(User.builder().build());

        String json = objectMapper.writeValueAsString(postCreateReq);
        MockMultipartFile request =
            new MockMultipartFile(
                "request", "json", "application/json", json.getBytes(StandardCharsets.UTF_8));

        PostCreateRes postCreateRes = PostCreateRes.builder().postId(postId).build();

        when(postService.createPost(eq(postCreateReq), eq(imageFile), eq(userDetails.getUser())))
                .thenReturn(postCreateRes);

        // when
        MvcResult result =
                mockMvc
                        .perform(
                                multipart("/v1/posts")
                                        .file(imageFile)
                                        .file(request)
                                        .principal(this.mockPrincipal))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        // then
        String responseJson = result.getResponse().getContentAsString();
        System.out.println(responseJson);
        assertThat(responseJson).isNotNull();
    }

    @Test
    @DisplayName("모집글 수정 테스트")
    void updatePostTest() throws Exception {
        // given
        Long postId = 1L;
        String title = "title";
        String content = "content";
        Timestamp deadline = Timestamp.valueOf(LocalDateTime.now());
        List<PostJobLimitReq> jobLimits =
            List.of(PostJobLimitReq.builder().headcount(1).job(Job.BACKEND).build());
        List<Skill> skills = List.of(Skill.JAVA, Skill.SPRING);
        PostUpdateReq postUpdateReq =
            PostUpdateReq.builder()
                .title(title)
                .content(content)
                .deadline(deadline)
                .jobLimits(jobLimits)
                .skills(skills)
                .build();

        Resource resource = new ClassPathResource("images/sparta.png");
        byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        MockMultipartFile imageFile = new MockMultipartFile(
            "image",
            "sparta.png",
            "image/png",
            imageBytes);

        UserDetailsImpl userDetails = new UserDetailsImpl(User.builder().build());

        String json = objectMapper.writeValueAsString(postUpdateReq);
        MockMultipartFile request =
            new MockMultipartFile(
                "request", "json", "application/json", json.getBytes(StandardCharsets.UTF_8));

        PostUpdateRes postUpdateRes = new PostUpdateRes();
        when(postService.updatePost(eq(postUpdateReq), eq(imageFile), eq(userDetails.getUser())))
            .thenReturn(postUpdateRes);

        // when
        MvcResult result = mockMvc.perform(
                multipart("/v1/posts")
                    .file(imageFile)
                    .file(request)
                    .principal(this.mockPrincipal))
            .andExpect(status().isOk())
            .andReturn();

        // then
        String responseJson = result.getResponse().getContentAsString();
        assertThat(responseJson).isNotNull();
    }

    @Test
    void deletePostTest() {}

    @Test
    void readPostTest() {}

    @Test
    void readPostListTest() {}

    @Test
    void updatePostStatusTest() {}
}
