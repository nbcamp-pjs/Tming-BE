package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostDeleteReq;
import com.spring.tming.domain.post.dto.request.PostUpdateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostStack;
import com.spring.tming.domain.post.entity.Skill;
import com.spring.tming.domain.post.entity.Status;
import com.spring.tming.domain.post.repository.JobLimitRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.post.repository.PostStackRepository;
import com.spring.tming.domain.post.util.ImageFileHandler;
import com.spring.tming.global.validator.PostValidator;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostStackRepository postStackRepository;
    private final JobLimitRepository jobLimitRepository;

    public PostCreateRes createPost(PostCreateReq postCreateReq, MultipartFile image)
            throws IOException {
        // postCreateReq로 들어온 값들에 대한 검증
        // title, content, deadline => validation 진행
        // skills 테이블 분리하지 않고 문자열 배열 형식 (회의후 진행)
        // image 처리 분리 => 저장소url 가져오기
        // s3에 업로드 진행방식 전에 서버에 저장하는 방식으로 진행 (리팩토링)
        String imageUrl = ImageFileHandler.uploadImage(image);

        Post savedPost =
                postRepository.save(
                        Post.builder()
                                .title(postCreateReq.getTitle())
                                .content(postCreateReq.getContent())
                                .deadline(postCreateReq.getDeadline())
                                .status(Status.OPEN)
                                .visit(0L)
                                .imageUrl(imageUrl)
                                .build());

        // 저장된 post로 postStack에도 저장
        // 테이블 분리할 경우 => for문으로 들어온 값 수만큼 저장 (보류)
        // Post테이블에 저장할 경우 들어온 값 그대로 저장 => 위에서 컬럼만들어서 진행
        savePostStackAndJobLimit(postCreateReq.getSkills(), postCreateReq.getJobLimits(), savedPost);

        return PostServiceMapper.INSTANCE.toPostCreateRes(savedPost);
    }

    @Transactional
    public void updatePost(PostUpdateReq postUpdateReq, MultipartFile image) throws IOException {
        // 해당하는 기존 모집글의 정보를 가져온다.
        Post post = postRepository.findByPostId(postUpdateReq.getPostId());
        // post가 없는 경우 validation 처리
        PostValidator.checkIsNullPost(post);
        // 모집글의 작성자와 인증된 유저가 같은지 확인 (validation)

        // 수정한 이미지 파일 처리
        String imageUrl = ImageFileHandler.uploadImage(image);

        // 업데이트한 Post 만들기
        Post updatedPost =
                postRepository.save(
                        Post.builder()
                                .postId(postUpdateReq.getPostId())
                                .title(postUpdateReq.getTitle())
                                .content(postUpdateReq.getContent())
                                .deadline(postUpdateReq.getDeadline())
                                .status(post.getStatus())
                                .visit(post.getVisit())
                                .imageUrl(imageUrl)
                                .build());

        // 해당 모집글에 관련된 엔티티 제거
        postStackRepository.deleteAllByPostId(postUpdateReq.getPostId());
        jobLimitRepository.deleteAllByPostId(postUpdateReq.getPostId());

        savePostStackAndJobLimit(postUpdateReq.getSkills(), postUpdateReq.getJobLimits(), updatedPost);
    }

    private void savePostStackAndJobLimit(
            List<Skill> skills, List<JobLimit> jobLimitList, Post post) {
        List<PostStack> postStacks = new ArrayList<>();
        skills.forEach(
                skill -> {
                    postStacks.add(PostStack.builder().skill(skill).post(post).build());
                });
        postStackRepository.saveAll(postStacks);

        // 저장된 post로 jobLimit에도 저장
        List<JobLimit> jobLimits = new ArrayList<>();
        jobLimitList.forEach(
                jobLimit -> {
                    jobLimits.add(
                            JobLimit.builder()
                                    .job(jobLimit.getJob())
                                    .headcount(jobLimit.getHeadcount())
                                    .post(post)
                                    .build());
                });
        jobLimitRepository.saveAll(jobLimits);
    }

    @Transactional
    public void deletePost(PostDeleteReq postDeleteReq) {
        Post post = postRepository.findByPostId(postDeleteReq.getPostId());
        PostValidator.checkIsNullPost(post);
        // 모집글의 작성자와 인증된 유저가 같은지 확인 (validation)

        postRepository.delete(post);
    }
}
