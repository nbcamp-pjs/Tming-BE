package com.spring.tming.domain.applicant.service;

import com.spring.tming.domain.applicant.dto.request.ApplicantCancelReq;
import com.spring.tming.domain.applicant.dto.request.ApplicantSaveReq;
import com.spring.tming.domain.applicant.dto.response.*;
import com.spring.tming.domain.applicant.entity.Applicant;
import com.spring.tming.domain.applicant.repository.ApplicantRepository;
import com.spring.tming.domain.applicant.validator.ApplicantValidator;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.PostValidator;
import com.spring.tming.global.validator.UserValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplicantSaveRes saveApplicantForPost(ApplicantSaveReq applicantSaveReq) {
        User user = getUserByUserId(applicantSaveReq.getUserId());
        Post post = getPostByPostId(applicantSaveReq.getPostId());
        Applicant applicant = applicantRepository.findByPostAndUser(post, user);
        ApplicantValidator.checkAlreadyApplied(applicant);
        applicantRepository.save(
                Applicant.builder().user(user).post(post).job(applicantSaveReq.getJob()).build());
        return new ApplicantSaveRes();
    }

    @Transactional
    public ApplicantCancelRes cancelApplicant(ApplicantCancelReq applicantCancelReq) {
        User user = getUserByUserId(applicantCancelReq.getUserId());
        Post post = getPostByPostId(applicantCancelReq.getPostId());
        Applicant applicant = applicantRepository.findByPostAndUser(post, user);
        ApplicantValidator.checkNotYetApplied(applicant);
        applicantRepository.delete(applicant);
        return new ApplicantCancelRes();
    }

    @Transactional(readOnly = true)
    public ApplicantGetResList getApplicants(Long postId) {
        List<ApplicantGetRes> applicantGetResList =
                ApplicantServiceMapper.INSTANCE.toApplicantGetResList(
                        applicantRepository.findByPostPostId(postId));
        return ApplicantGetResList.builder()
                .applicants(applicantGetResList)
                .total(applicantGetResList.size())
                .build();
    }

    private User getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }

    private Post getPostByPostId(Long postId) {
        Post post = postRepository.findByPostId(postId);
        PostValidator.checkIsNullPost(post);
        return post;
    }
}
