package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.request.PostCreateReq;
import com.spring.tming.domain.post.dto.request.PostDeleteReq;
import com.spring.tming.domain.post.dto.request.PostJobLimitReq;
import com.spring.tming.domain.post.dto.request.PostReadReq;
import com.spring.tming.domain.post.dto.request.PostStatusUpdateReq;
import com.spring.tming.domain.post.dto.request.PostUpdateReq;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostDeleteRes;
import com.spring.tming.domain.post.dto.response.PostReadRes;
import com.spring.tming.domain.post.dto.response.PostReadResList;
import com.spring.tming.domain.post.dto.response.PostStatusUpdateRes;
import com.spring.tming.domain.post.dto.response.PostUpdateRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostStack;
import com.spring.tming.domain.post.repository.JobLimitRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.post.repository.PostStackRepository;
import com.spring.tming.domain.post.util.ImageFileHandler;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Skill;
import com.spring.tming.global.meta.Status;
import com.spring.tming.global.redis.RedisUtil;
import com.spring.tming.global.s3.S3Provider;
import com.spring.tming.global.validator.PostValidator;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostStackRepository postStackRepository;
    private final JobLimitRepository jobLimitRepository;
    private final S3Provider s3Provider;
    private final RedisUtil redisUtil;
    private static final String VISIT_KEY = "USER";
    private static final String VISIT_VALUE = "POST";

    @Transactional
    public PostCreateRes createPost(PostCreateReq postCreateReq, MultipartFile image, User user)
            throws IOException {
        PostValidator.checkRequest(postCreateReq.getTitle(), postCreateReq.getContent());
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
        PostValidator.checkRequest(postUpdateReq.getTitle(), postUpdateReq.getContent());
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
            List<Skill> skills, List<PostJobLimitReq> jobLimitList, Post post) {
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

    @Transactional
    public PostReadRes readPost(Long postId, User user) {
        Post post = postRepository.findByPostId(postId);
        Timestamp createTimestamp = post.getCreateTimestamp();
        PostValidator.checkIsNullPost(post);
        if (!Objects.equals(post.getUser().getUserId(), user.getUserId())
                && !redisUtil
                        .getValuesList(VISIT_KEY + user.getUserId().toString())
                        .contains(VISIT_VALUE + postId.toString())) {
            redisUtil.setValuesList(
                    VISIT_KEY + user.getUserId().toString(), VISIT_VALUE + postId.toString());
            postRepository.save(
                    Post.builder()
                            .postId(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .deadline(post.getDeadline())
                            .status(post.getStatus())
                            .visit(post.getVisit() + 1)
                            .imageUrl(post.getImageUrl())
                            .user(post.getUser())
                            .build());
            Post changedPost = postRepository.findByPostId(postId);
            changedPost.setCreateTimestamp(createTimestamp);
            //            Timestamp c = changedPost.getCreateTimestamp();
            return PostServiceMapper.INSTANCE.toPostReadRes(changedPost, user.getUserId());
        }

        return PostServiceMapper.INSTANCE.toPostReadRes(post, user.getUserId());
    }

    // TODO: MEMBER
    @Transactional(readOnly = true)
    public PostReadResList readPostList(PostReadReq dto, User user) {
        switch (dto.getType()) {
            case ALL:
                {
                    Page<Post> posts =
                            postRepository.getAllPost(dto.getSkill(), dto.getJob(), dto.getPageRequest());
                    return PostReadResList.builder()
                            .postAllReadRes(PostServiceMapper.INSTANCE.toPostAllReadResList(posts.getContent()))
                            .totalCount(posts.getTotalElements())
                            .totalPage(posts.getTotalPages())
                            .pageNumber(dto.getPageRequest().getPageNumber() + 1)
                            .build();
                }
            case LIKE:
                {
                    Page<Post> posts =
                            postRepository.getAllPostByLike(
                                    user, dto.getSkill(), dto.getJob(), dto.getPageRequest());
                    return PostReadResList.builder()
                            .postAllReadRes(PostServiceMapper.INSTANCE.toPostAllReadResList(posts.getContent()))
                            .totalCount(posts.getTotalElements())
                            .totalPage(posts.getTotalPages())
                            .pageNumber(dto.getPageRequest().getPageNumber() + 1)
                            .build();
                }
            case APPLY:
                {
                    Page<Post> posts =
                            postRepository.getAllPostByApply(
                                    user, dto.getSkill(), dto.getJob(), dto.getPageRequest());
                    return PostReadResList.builder()
                            .postAllReadRes(PostServiceMapper.INSTANCE.toPostAllReadResList(posts.getContent()))
                            .totalCount(posts.getTotalElements())
                            .totalPage(posts.getTotalPages())
                            .pageNumber(dto.getPageRequest().getPageNumber() + 1)
                            .build();
                }
            case WRITE:
                {
                    Page<Post> posts =
                            postRepository.getAllPostByUser(
                                    user, dto.getSkill(), dto.getJob(), dto.getPageRequest());
                    return PostReadResList.builder()
                            .postAllReadRes(PostServiceMapper.INSTANCE.toPostAllReadResList(posts.getContent()))
                            .totalCount(posts.getTotalElements())
                            .totalPage(posts.getTotalPages())
                            .pageNumber(dto.getPageRequest().getPageNumber() + 1)
                            .build();
                }
            case MEMBER:
                {
                    Page<Post> posts =
                            postRepository.getAllPostByMember(
                                    user, dto.getSkill(), dto.getJob(), dto.getPageRequest());
                    return PostReadResList.builder()
                            .postAllReadRes(PostServiceMapper.INSTANCE.toPostAllReadResList(posts.getContent()))
                            .totalCount(posts.getTotalElements())
                            .totalPage(posts.getTotalPages())
                            .pageNumber(dto.getPageRequest().getPageNumber() + 1)
                            .build();
                }
            default:
                return PostReadResList.builder().build();
        }
    }

    @Transactional
    public PostStatusUpdateRes updatePostStatus(PostStatusUpdateReq postStatusUpdateReq, User user) {
        Post post = postRepository.findByPostId(postStatusUpdateReq.getPostId());
        PostValidator.checkIsNullPost(post);
        PostValidator.checkIsPostUser(post, user);
        postRepository.save(post.toBuilder().status(postStatusUpdateReq.getStatus()).build());
        return new PostStatusUpdateRes();
    }
}
