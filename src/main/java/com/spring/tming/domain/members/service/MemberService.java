package com.spring.tming.domain.members.service;

import com.spring.tming.domain.members.dto.request.EmitMemberReq;
import com.spring.tming.domain.members.dto.response.EmitMemberRes;
import com.spring.tming.domain.members.entity.Member;
import com.spring.tming.domain.members.repository.MemberRepository;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.MemberValidator;
import com.spring.tming.global.validator.PostValidator;
import com.spring.tming.global.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public EmitMemberRes emitMember(User user, EmitMemberReq request) {
        Post post = getPost(request.getPostId(), user);
        User emitUser = getUser(request.getUserId());
        Member member = getMember(post, emitUser);
        memberRepository.delete(member);
        return new EmitMemberRes();
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
