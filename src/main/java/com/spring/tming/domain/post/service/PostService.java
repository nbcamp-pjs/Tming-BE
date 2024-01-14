package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostDeleteReq;
import com.spring.tming.domain.post.dto.request.PostUpdateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostDeleteRes;
import com.spring.tming.domain.post.dto.response.PostReadRes;
import com.spring.tming.domain.post.dto.response.PostReadResList;
import com.spring.tming.domain.post.dto.response.PostUpdateRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostStack;
import com.spring.tming.domain.post.entity.Skill;
import com.spring.tming.domain.post.entity.Status;
import com.spring.tming.domain.post.enums.Type;
import com.spring.tming.domain.post.repository.JobLimitRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.post.repository.PostStackRepository;
import com.spring.tming.domain.post.util.ImageFileHandler;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.s3.S3Provider;
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
    private final UserRepository userRepository;
    private final S3Provider s3Provider;

    public PostCreateRes createPost(PostCreateReq postCreateReq, MultipartFile image, User user)
            throws IOException {
        // TODO: postCreateReq로 들어온 값들에 대한 검증(title, content, deadline => validation 진행)

        // image 처리 분리 => 저장소url 가져오기
        String imageUrl = s3Provider.saveFile(image, "postImage");
        Post savedPost =
                postRepository.save(
                        Post.builder()
                                .title(postCreateReq.getTitle())
                                .content(postCreateReq.getContent())
                                .deadline(postCreateReq.getDeadline())
                                .status(Status.OPEN)
                                .visit(0L)
                                .imageUrl(imageUrl)
                                .user(user)
                                .build());

        // 저장된 post로 postStack, jobLimit에도 저장
        savePostStackAndJobLimit(postCreateReq.getSkills(), postCreateReq.getJobLimits(), savedPost);

        return PostServiceMapper.INSTANCE.toPostCreateRes(savedPost);
    }

    @Transactional
    public PostUpdateRes updatePost(PostUpdateReq postUpdateReq, MultipartFile image, User user)
            throws IOException {
        // 해당하는 기존 모집글의 정보를 가져온다.
        Post post = postRepository.findByPostId(postUpdateReq.getPostId());
        // post가 없는 경우 validation 처리
        PostValidator.checkIsNullPost(post);
        // 모집글의 작성자와 인증된 유저가 같은지 확인 (validation)
        PostValidator.checkIsPostUser(post, user);
        // 수정한 이미지 파일 처리
        String imageUrl = ImageFileHandler.checkUpdateImage(post, image);

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
                                .user(user)
                                .build());

        // 해당 모집글에 관련된 엔티티 제거
        postStackRepository.deleteByPostPostId(postUpdateReq.getPostId());
        jobLimitRepository.deleteByPostPostId(postUpdateReq.getPostId());

        savePostStackAndJobLimit(postUpdateReq.getSkills(), postUpdateReq.getJobLimits(), updatedPost);
        return new PostUpdateRes();
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
    public PostDeleteRes deletePost(PostDeleteReq postDeleteReq, User user) {
        Post post = postRepository.findByPostId(postDeleteReq.getPostId());
        PostValidator.checkIsNullPost(post);
        // 모집글의 작성자와 인증된 유저가 같은지 확인 (validation)
        PostValidator.checkIsPostUser(post, user);
        postRepository.delete(post);
        return new PostDeleteRes();
    }

    // TODO: Redis로 조회수 올리기
    public PostReadRes readPost(Long postId) {
        // 포스트 단건 조회
        // 1. 포스트 정보 (post, postStack, jobLimit) (Ok!)
        // 2. like에서 count해서 가져오기 (보류)
        // 3. member에서 정보 가져오기 (보류)
        Post post = postRepository.findByPostId(postId);
        PostValidator.checkIsNullPost(post);
        User writer = post.getUser();
        List<Skill> skills = new ArrayList<>();
        List<PostStack> postStacks = postStackRepository.findAllByPostPostId(postId);
        postStacks.forEach(postStack -> skills.add(postStack.getSkill()));
        List<JobLimit> jobLimits = jobLimitRepository.findAllByPostPostId(postId);

        PostReadRes postReadRes =
                PostReadRes.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .deadline(post.getDeadline())
                        .visit(post.getVisit())
                        .like(0L) // 보류
                        .imageUrl(post.getImageUrl())
                        .status(post.getStatus())
                        .username(writer.getUsername())
                        .jobLimits(jobLimits)
                        .skills(skills)
//                    .members()
                        .build();
        return postReadRes;
    }

    // TODO: 페이징처리하기
    public PostReadResList readPostList(Type type, User user) {
        Type checkedType = PostValidator.checkIsValidType(type);
        PostReadResList postReadResList = null;
        switch (checkedType) {
            case ALL: {
                List<Post> posts = postRepository.findAllByOrderByCreateTimestampAsc();
                List<PostReadRes> postReadRes = new ArrayList<>();
                posts.forEach(post -> {
                    User writer = post.getUser();
                    List<JobLimit> jobLimits = post.getJobLimits();
                    postReadRes.add(PostReadRes.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .deadline(post.getDeadline())
                        .visit(post.getVisit())
                        .like(0L) // 보류
                        .imageUrl(post.getImageUrl())
                        .status(post.getStatus())
                        .username(writer.getUsername())
                        .jobLimits(jobLimits)
                        .build()
                    );
                });
                postReadResList = PostReadResList.builder()
                    .postReadRes(postReadRes)
                    .build();
            }
            case LIKE: {
                // 보류
            }
            case APPLY: {
                // 보류
            }
            case WRITE: {
                List<Post> posts = postRepository.findAllByUserUserIdOrderByCreateTimestampAsc(user.getUserId());
                List<PostReadRes> postReadRes = new ArrayList<>(); // TODO: 다른 것들도 구현 후 메서드로 리팩토링
                posts.forEach(post -> {
                    User writer = post.getUser();
                    List<JobLimit> jobLimits = post.getJobLimits();
                    postReadRes.add(PostReadRes.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .deadline(post.getDeadline())
                        .visit(post.getVisit())
                        .like(0L) // 보류
                        .imageUrl(post.getImageUrl())
                        .status(post.getStatus())
                        .username(writer.getUsername())
                        .jobLimits(jobLimits)
                        .build()
                    );
                });
                postReadResList = PostReadResList.builder()
                    .postReadRes(postReadRes)
                    .build();
            }
            case MEMBER: {
                // 보류
            }
        }
        return postReadResList;
    }
}
