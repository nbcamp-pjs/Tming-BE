package com.spring.tming.domain.members.service;

import com.spring.tming.domain.members.dto.request.EmitMemberReq;
import com.spring.tming.domain.members.dto.request.MemberAdmitReq;
import com.spring.tming.domain.members.dto.response.EmitMemberRes;
import com.spring.tming.domain.members.dto.response.MemberAdmitRes;
import com.spring.tming.domain.members.dto.response.MemberGetRes;
import com.spring.tming.domain.members.dto.response.MemberGetResList;
import com.spring.tming.domain.members.entity.Member;
import com.spring.tming.domain.members.repository.MemberRepository;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.MemberValidator;
import com.spring.tming.global.validator.PostValidator;
import com.spring.tming.global.validator.UserValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public MemberAdmitRes admitMember(MemberAdmitReq memberAdmitReq) {
        User user = getUser(memberAdmitReq.getUserId());
        Post post = getPost(memberAdmitReq.getPostId(), user);
        Member member = memberRepository.findByPostAndUser(post, user);
        MemberValidator.checkAlreadyAdmitted(member);
        MemberValidator.checkCancelBeforeAdmit(member);
        memberRepository.save(
                Member.builder().user(user).post(post).job(memberAdmitReq.getJob()).build());
        return new MemberAdmitRes();
    }

    @Transactional
    public EmitMemberRes emitMember(User user, EmitMemberReq request) {
        Post post = getPost(request.getPostId(), user);
        User emitUser = getUser(request.getUserId());
        Member member = getMember(post, emitUser);
        memberRepository.delete(member);
        return new EmitMemberRes();
    }

    @Transactional(readOnly = true)
    public MemberGetResList getMembers(Long postId) {
        List<MemberGetRes> memberGetResList =
                MemberServiceMapper.INSTANCE.toMemberGetResList(memberRepository.findByPostPostId(postId));
        return MemberGetResList.builder()
                .members(memberGetResList)
                .total(memberGetResList.size())
                .build();
    }

    private Post getPost(Long postId, User user) {
        Post post = postRepository.findByPostIdAndUser(postId, user);
        PostValidator.checkIsNullPost(post);
        return post;
    }

    private User getUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }

    private Member getMember(Post post, User user) {
        Member member = memberRepository.findByPostAndUser(post, user);
        MemberValidator.validate(member);
        return member;
    }
}
