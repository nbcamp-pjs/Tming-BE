package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostStack;
import com.spring.tming.domain.post.entity.Status;
import com.spring.tming.domain.post.repository.JobLimitRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.post.repository.PostStackRepository;
import com.spring.tming.domain.post.util.ImageFileHandler;
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
        List<PostStack> postStacks = new ArrayList<>();
        postCreateReq
                .getSkills()
                .forEach(
                        skill -> {
                            postStacks.add(PostStack.builder().skill(skill).post(savedPost).build());
                        });
        postStackRepository.saveAll(postStacks);

        // 저장된 post로 jobLimit에도 저장
        List<JobLimit> jobLimits = new ArrayList<>();
        postCreateReq
                .getJobLimits()
                .forEach(
                        jobLimit -> {
                            jobLimits.add(
                                    JobLimit.builder()
                                            .job(jobLimit.getJob())
                                            .headcount(jobLimit.getHeadcount())
                                            .post(savedPost)
                                            .build());
                        });
        jobLimitRepository.saveAll(jobLimits);

        return PostServiceMapper.INSTANCE.toPostCreateRes(savedPost);
    }
}
