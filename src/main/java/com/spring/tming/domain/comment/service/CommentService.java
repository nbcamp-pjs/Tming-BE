package com.spring.tming.domain.comment.service;

import com.spring.tming.domain.comment.dto.request.CommentSaveReq;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.entity.Comment;
import com.spring.tming.domain.comment.repository.CommentRepository;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.PostValidator;
import com.spring.tming.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq commentSaveReq) {
        User user = getUserByUserId(commentSaveReq.getUserId());
        Post post = getPostByPostId(commentSaveReq.getPostId());
        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder().content(commentSaveReq.getContent()).user(user).post(post).build()));
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
