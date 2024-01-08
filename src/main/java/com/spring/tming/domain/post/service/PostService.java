package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.Status;
import com.spring.tming.domain.post.repository.JobLimitRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.post.repository.PostStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostStackRepository postStackRepository;
    private final JobLimitRepository jobLimitRepository;

    public PostCreateRes createPost(PostCreateReq postCreateReq) {
        // postCreateReq로 들어온 값들에 대한 검증
        // title, content, deadline => validation 진행
        // skills 테이블 분리하지 않고 문자열 배열 형식 (회의후 진행)
        // image 처리 분리 => 저장소url 가져오기
        Post savedPost =
                postRepository.save(
                        Post.builder()
                                .title(postCreateReq.getTitle())
                                .content(postCreateReq.getContent())
                                .deadline(postCreateReq.getDeadline())
                                .status(Status.OPEN)
                                .visit(0L)
                                .imageUrl(postCreateReq.getImage())
                                .build());

        // 저장된 post로 postStack에도 저장
        // 테이블 분리할 경우 => for문으로 들어온 값 수만큼 저장 (보류)
        // Post테이블에 저장할 경우 들어온 값 그대로 저장 => 위에서 컬럼만들어서 진행

        // 저장된 post로 jobLimit에도 저장
        postCreateReq
                .getJobLimitList()
                .forEach(
                        jobLimit -> {
                            jobLimitRepository.save(
                                    JobLimit.builder()
                                            .job(jobLimit.getJob())
                                            .headcount(jobLimit.getHeadcount())
                                            .post(savedPost)
                                            .build());
                        });

        return PostServiceMapper.INSTANCE.toPostCreateRes(savedPost);
    }
}
