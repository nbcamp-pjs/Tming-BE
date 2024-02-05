package com.spring.tming.domain.post.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostJobLimitReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.JobLimitRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.post.repository.PostStackRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.service.UserService;
import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import com.spring.tming.global.s3.S3Provider;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StreamUtils;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks private PostService postService;
    @Mock private PostRepository postRepository;
    @Mock private PostStackRepository postStackRepository;
    @Mock private JobLimitRepository jobLimitRepository;
    @Mock private UserService userService;
    @Mock private S3Provider s3Provider;

    @Test
    @DisplayName("모집글 생성 테스트")
    void createPost() throws IOException {
        // given
        User user = User.builder().build();
        Resource resource = new ClassPathResource("images/sparta.png");
        byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "sparta.png", "image/png", imageBytes);

        PostCreateReq postCreateReq =
                PostCreateReq.builder()
                        .title("Test Title")
                        .content("Test Content")
                        .deadline(new Timestamp(System.currentTimeMillis()))
                        .skills(List.of(Skill.JAVA, Skill.SPRING))
                        .jobLimits(List.of(PostJobLimitReq.builder().job(Job.BACKEND).headcount(5).build()))
                        .build();

        when(s3Provider.saveFile(any(), any())).thenReturn("url");
        when(postRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        PostCreateRes result = postService.createPost(postCreateReq, imageFile, user);

        // then
        verify(s3Provider, times(1)).saveFile(eq(imageFile), eq("postImage"));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("모집글 수정 테스트")
    void updatePost() throws IOException {}

    @Test
    @DisplayName("모집글 삭제 테스트")
    void deletePost() {}

    @Test
    @DisplayName("모집글 단건조회 테스트")
    void readPost() {}

    @Test
    @DisplayName("모집글 전체 조회 테스트")
    void readPostList() {}

    @Test
    @DisplayName("모집글 상태 변경 테스트")
    void updatePostStatus() {}
}
