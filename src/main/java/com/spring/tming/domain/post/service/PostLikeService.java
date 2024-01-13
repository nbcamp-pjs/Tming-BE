package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.request.PostLikeReq;
import com.spring.tming.domain.post.dto.response.PostLikeRes;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostLike;
import com.spring.tming.domain.post.repository.PostLikeRepository;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.PostValidator;
import com.spring.tming.global.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostLikeRes likePost(PostLikeReq postLikeReq) {
        User user = getUserByUserId(postLikeReq.getUserId());
        Post post = getPostByPostId(postLikeReq.getPostId());
        PostLike postLike = postLikeRepository.findByUserAndPost(user, post);
        PostValidator.checkAlreadyLiked(postLike);
        postLikeRepository.save(postLike);
        return new PostLikeRes();
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
